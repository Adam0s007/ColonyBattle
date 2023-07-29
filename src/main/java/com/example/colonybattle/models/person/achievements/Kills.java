package com.example.colonybattle.models.person.achievements;

import com.example.colonybattle.models.person.Person;
import java.util.HashSet;
import java.util.Set;

public class Kills {

    private Set<Person> killedPersons;

    public Kills() {
        this.killedPersons = new HashSet<>();
    }

    public void addKill(Person person) {
        this.killedPersons.add(person);

    }

    public boolean hasKilled(Person person) {
        return this.killedPersons.contains(person);
    }

    public Set<Person> getKilledPersons() {
        return this.killedPersons;
    }

    public int getKills() {
        return this.killedPersons.size();
    }
}
