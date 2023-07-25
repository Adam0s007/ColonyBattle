package com.example.colonybattle.models.person.actions.defense;

import com.example.colonybattle.models.person.Person;

public interface DefendStrategy {
    void defend(Person attacker, int damage);
}
