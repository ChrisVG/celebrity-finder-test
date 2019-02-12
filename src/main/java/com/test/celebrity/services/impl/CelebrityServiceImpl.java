package com.test.celebrity.services.impl;

import com.test.celebrity.exception.CelebrityFinderException;
import com.test.celebrity.model.Person;
import com.test.celebrity.services.CelebrityService;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

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
     * @throws CelebrityFinderException
     */
    @Override
    public Person findCelebrity(Set<Person> people) throws CelebrityFinderException {
        if (people.size() < 2)
            throw new CelebrityFinderException("Party must have more than one person");
        Deque<Person> possibleCelebrities = new ArrayDeque<>();
        // Step 1 :Push all party members  onto stack
        people.forEach(person -> possibleCelebrities.push(person));
        while (possibleCelebrities.size() > 1) {
            // Step 2 :Pop the top two elements from the
            // stack, discard one person based on haveAcquaintance(person1, person2).
            Person personOne = possibleCelebrities.pop();
            Person personTwo = possibleCelebrities.pop();
            // Step 3 : return the remained person to the stack.
            if (haveAcquaintance(personOne, personTwo))
                possibleCelebrities.push(personTwo);
            else
                possibleCelebrities.push(personOne);
        }
        Person lastPerson = possibleCelebrities.pop();
        // Step 5 : Check if the last person is celebrity or not
        for (Person person : people) {
            if (!lastPerson.equals(person) && (haveAcquaintance(lastPerson, person) || !haveAcquaintance(person, lastPerson)))
                throw new CelebrityFinderException("Celebrity is not present");
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
