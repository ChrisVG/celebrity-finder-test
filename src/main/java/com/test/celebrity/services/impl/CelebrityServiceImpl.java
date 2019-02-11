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
    @Override
    public Person findCelebrity(List<Person> people) throws Exception {
        Stack<Person> possibleCelebrities = new Stack<>();
        people.forEach(person -> possibleCelebrities.push(person));
        while (possibleCelebrities.size() > 1) {
            // Step 2 :Pop off top two persons from the
            // stack, discard one person based on return
            // status of knows(A, B).
            Person elementOne = possibleCelebrities.pop();
            Person elementTwo = possibleCelebrities.pop();
            // Step 3 : Push the remained person onto stack.
            if (haveAcquaintance(elementOne, elementTwo))
                possibleCelebrities.push(elementTwo);
            else
                possibleCelebrities.push(elementOne);
        }
        Person lastElement = possibleCelebrities.pop();
        // Step 5 : Check if the last person is
        // celebrity or not
        for (Person person : people) {
            // If any person doesn't know 'c' or 'a'
            // doesn't know any person, return -1
            if (!lastElement.equals(person) && (haveAcquaintance(lastElement, person) || !haveAcquaintance(person, lastElement)))
                throw new Exception("Celebrity is not present");
        }
        return lastElement;
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
