package com.test.celebrity.services.impl;

import com.test.celebrity.model.Person;
import com.test.celebrity.services.CelebrityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * CelebrityServiceImpl
 *
 * @author christian.valencia
 * @since 11/02/2019
 */
@Service
public class CelebrityServiceImpl implements CelebrityService {
    /**
     * get a celebrity <p>(Person)</p> based on a N number of people
     * <p>
     * if party has les than 2 elements system will throw an exception
     * if all the people in the party don't know any person, system will return an exception
     *
     * @param people
     * @return
     * @throws Exception
     */
    @Override
    public Person findCelebrity(Set<Person> people) throws Exception {
        if (people.size() < 2)
            throw new Exception("Party must have more than one person");
        Stack<Person> possibleCelebrities = new Stack<>();
        // Step 1 :Push everybody onto stack
        people.forEach(person -> possibleCelebrities.push(person));
        while (possibleCelebrities.size() > 1) {
            // Step 2 :Pop off top two persons from the
            // stack, discard one person based on return
            // status of knows(A, B).
            Person personOne = possibleCelebrities.pop();
            Person personTwo = possibleCelebrities.pop();
            // Step 3 : Push the remained person onto stack.
            if (haveAcquaintance(personOne, personTwo))
                possibleCelebrities.push(personTwo);
            else
                possibleCelebrities.push(personOne);
        }
        Person lastPerson = possibleCelebrities.pop();
        // Step 5 : Check if the last person is
        // celebrity or not
        for (Person person : people) {
            // If any person doesn't know 'c' or 'a'
            // doesn't know any person, return -1
            if (!lastPerson.equals(person) && (haveAcquaintance(lastPerson, person) || !haveAcquaintance(person, lastPerson)))
                throw new Exception("Celebrity is not present");
        }
        return lastPerson;
    }

    /**
     * Checks if person {@param elementOne} knows person {@param elementTwo}.
     *
     * @param elementOne
     * @param elementTwo
     * @return
     */
    public boolean haveAcquaintance(Person elementOne, Person elementTwo) {
        return elementOne.getKnownPeople().contains(elementTwo);
    }
}
