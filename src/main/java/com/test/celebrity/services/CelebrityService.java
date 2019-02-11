package com.test.celebrity.services;

import com.test.celebrity.model.Person;

import java.util.List;
import java.util.Set;

/**
 * CelebrityService
 *
 * @author christian.valencia
 * @since 11/02/2019
 */
public interface CelebrityService {

    public Person findCelebrity(List<Person> people) throws Exception;
}
