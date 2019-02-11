package com.test.celebrity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.celebrity.app.CelebrityFinderTestApplication;
import com.test.celebrity.controller.CelebrityController;
import com.test.celebrity.model.Person;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CelebrityFinderTestApplication.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)

public class CelebrityFinderTestApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(CelebrityFinderTestApplicationTests.class);

    private MockMvc mockMvc;
    ObjectMapper mapper;
    private final String PARTY_WITHOUT_CELEBRITY = "[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"},{\"id\":2,\"firstName\":\"Katherine\",\"knownPeople\":[],\"lastName\":\"Fuentes\"},{\"id\":3,\"firstName\":\"John\",\"knownPeople\":[],\"lastName\":\"Doe\"},{\"id\":4,\"firstName\":\"Merry\",\"knownPeople\":[],\"lastName\":\"Christmas\"}][{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"},{\"id\":2,\"firstName\":\"Katherine\",\"knownPeople\":[],\"lastName\":\"Fuentes\"},{\"id\":3,\"firstName\":\"John\",\"knownPeople\":[],\"lastName\":\"Doe\"},{\"id\":4,\"firstName\":\"Merry\",\"knownPeople\":[],\"lastName\":\"Christmas\"}]";
    private final String PARTY_WITH_CELEBRITY_AND_OTHER_KNOWN_PEOPLE= "[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"},{\"id\":2,\"firstName\":\"Katherine\",\"knownPeople\":[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"}],\"lastName\":\"Fuentes\"},{\"id\":3,\"firstName\":\"John\",\"knownPeople\":[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"},{\"id\":2,\"firstName\":\"Katherine\",\"knownPeople\":[],\"lastName\":\"Fuentes\"}],\"lastName\":\"Doe\"},{\"id\":4,\"firstName\":\"Merry\",\"knownPeople\":[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"},{\"id\":3,\"firstName\":\"John\",\"knownPeople\":[],\"lastName\":\"Doe\"}],\"lastName\":\"Christmas\"}][{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"},{\"id\":2,\"firstName\":\"Katherine\",\"knownPeople\":[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"}],\"lastName\":\"Fuentes\"},{\"id\":3,\"firstName\":\"John\",\"knownPeople\":[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"},{\"id\":2,\"firstName\":\"Katherine\",\"knownPeople\":[],\"lastName\":\"Fuentes\"}],\"lastName\":\"Doe\"},{\"id\":4,\"firstName\":\"Merry\",\"knownPeople\":[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"},{\"id\":3,\"firstName\":\"John\",\"knownPeople\":[],\"lastName\":\"Doe\"}],\"lastName\":\"Christmas\"}]";
    private final String PARTY_WITH_CELEBRITY  = "[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"},{\"id\":2,\"firstName\":\"Katherine\",\"knownPeople\":[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"}],\"lastName\":\"Fuentes\"},{\"id\":3,\"firstName\":\"John\",\"knownPeople\":[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"}],\"lastName\":\"Doe\"},{\"id\":4,\"firstName\":\"Merry\",\"knownPeople\":[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"}],\"lastName\":\"Christmas\"}][{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"},{\"id\":2,\"firstName\":\"Katherine\",\"knownPeople\":[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"}],\"lastName\":\"Fuentes\"},{\"id\":3,\"firstName\":\"John\",\"knownPeople\":[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"}],\"lastName\":\"Doe\"},{\"id\":4,\"firstName\":\"Merry\",\"knownPeople\":[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"}],\"lastName\":\"Christmas\"}]";
    private final String PARTY_WITH_JUST_ONE_PERSON  = "[{\"id\":\"1\",\"firstName\":\"Christian\",\"knownPeople\":[],\"lastName\":\"Valencia\"}]";
    private Person expectedResultPartyWithCelebrity;
    @Autowired
    private WebApplicationContext wac;
    private Person celebrity;

    @Before
    public void setup() {
        this.expectedResultPartyWithCelebrity = new Person();
        this.expectedResultPartyWithCelebrity.setId(1);
        this.expectedResultPartyWithCelebrity.setFirstName("Christian");
        this.expectedResultPartyWithCelebrity.setLastName("Valencia");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mapper = new ObjectMapper();
    }

    @Test
    public void contextLoads() {
    }


    @Test
    public void testPartyWithoutCelebrity() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/find-celebrity")
                .content(PARTY_WITHOUT_CELEBRITY)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andReturn();
        LOGGER.info(new String(mvcResult.getResponse().getContentAsByteArray()));
    }

    @Test
    public void testPartyWithCelebrity() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/find-celebrity")
                .content(PARTY_WITH_CELEBRITY)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(expectedResultPartyWithCelebrity.getId())))
                .andExpect(jsonPath("$.firstName", is(expectedResultPartyWithCelebrity.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expectedResultPartyWithCelebrity.getLastName()))).andDo(print())
                .andReturn();
        this.celebrity = this.mapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(), Person.class);
        LOGGER.info(celebrity.toString());
    }

    @Test
    public void testPartyWithCelebrityAndMoreThanOneKnowledgePerson() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/find-celebrity")
                .content(PARTY_WITH_CELEBRITY_AND_OTHER_KNOWN_PEOPLE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(expectedResultPartyWithCelebrity.getId())))
                .andExpect(jsonPath("$.firstName", is(expectedResultPartyWithCelebrity.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expectedResultPartyWithCelebrity.getLastName()))).andDo(print())
                .andReturn();
        this.celebrity = this.mapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(), Person.class);
        LOGGER.info(celebrity.toString());
    }

    @Test
    public void testOneMemberParty() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/find-celebrity")
                .content(PARTY_WITH_JUST_ONE_PERSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andReturn();
        LOGGER.info(new String(mvcResult.getResponse().getContentAsByteArray()));
    }
}

