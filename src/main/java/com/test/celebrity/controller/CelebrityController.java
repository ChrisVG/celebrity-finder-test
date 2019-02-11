package com.test.celebrity.controller;

import com.test.celebrity.model.Person;
import com.test.celebrity.services.CelebrityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CelebrityController
 *
 * @author christian.valencia
 * @since 11/02/2019
 */
@RestController
@RequestMapping("/find-celebrity")
public class CelebrityController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CelebrityController.class);

    @Autowired
    private CelebrityService celebrityService;

    /**
     * @param partyMembers
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<?> getRandomMessage(@RequestBody List<Person> partyMembers) {
        try {
            return ResponseEntity.ok(celebrityService.findCelebrity(partyMembers));
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }


}