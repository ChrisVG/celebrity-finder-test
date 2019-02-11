package com.test.celebrity.model;

import java.util.Objects;
import java.util.Set;

/**
 * Person
 *
 * @author christian.valencia
 * @since 11/02/2019
 */
public class Person {
    private String id;
    private String firstName;
    private String lastName;

    private Set<Person> knownPeople;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Person> getKnownPeople() {
        return knownPeople;
    }

    public void setKnownPeople(Set<Person> knownPeople) {
        this.knownPeople = knownPeople;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName);
    }
}
