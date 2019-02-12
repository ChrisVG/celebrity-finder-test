package com.test.celebrity.services;

import com.test.celebrity.exception.CelebrityFinderException;
import com.test.celebrity.model.Person;

import java.util.Set;

/**
 * CelebrityService
 *
 * @author christian.valencia
 * @since 11/02/2019
 */
public interface CelebrityService {

    /** get a celebrity <p>(Person)</p> based on a N number of people
     *
     * @param people
     * @return
     * @throws Exception
     */
    public Person findCelebrity(Set<Person> people) throws CelebrityFinderException;
}
