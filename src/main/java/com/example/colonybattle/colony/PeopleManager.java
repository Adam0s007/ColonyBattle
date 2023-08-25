package com.example.colonybattle.colony;

import com.example.colonybattle.models.person.Person;
import lombok.Getter;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
@Getter
public class PeopleManager {
    private Set<Person> people;

    public PeopleManager(Set<Person> people) {
        this.people = ConcurrentHashMap.newKeySet();
        this.people.addAll(people);
    }
    public PeopleManager() {
        this.people = ConcurrentHashMap.newKeySet();
    }

    public void addPeople(Set<Person> people, Colony colony) {
        this.people.addAll(people);
        for (Person person : people) {
            person.setColony(colony);
        }
    }

    public void addPerson(Person person) {
        this.people.add(person);
    }

    public void removePerson(Person person) {
        this.people.remove(person);
    }

    public int getTotalPeopleCount() {
        return people.size();
    }

    public int getPeopleCount() {
        return people.size();
    }
}
