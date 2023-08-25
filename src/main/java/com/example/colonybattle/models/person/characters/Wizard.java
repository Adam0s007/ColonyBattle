package com.example.colonybattle.models.person.characters;

import com.example.colonybattle.board.position.finder.WizardClosestPositionFinder;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.config.WizardMagic;
import com.example.colonybattle.models.person.actions.magic.AbsorbCommand;
import com.example.colonybattle.models.person.actions.magic.HealCommand;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.actions.attack.WizardAttackStrategy;
import com.example.colonybattle.models.person.actions.defense.WizardDefendStrategy;
import com.example.colonybattle.models.person.actions.movement.WizardMovementStrategy;
import com.example.colonybattle.models.person.type.PersonType;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class Wizard extends Person {


    public final int MIN_WAIT = 800;
    public final int MAX_WAIT = 1000;

    public Wizard(PersonType type, Point2d position, Colony colony, int id) {
        super(type.getHealth(), type.getEnergy(), type.getStrength(), position, colony, type.getLandAppropriation(),id);  // Wartość 10 to przykładowa wartość landAppropriation dla Warrior
        super.movement = new WizardMovementStrategy(this);
        this.attackPerformer = new WizardAttackStrategy(this,movement);
        this.status.setType(type);
        this.defendStrategy = new WizardDefendStrategy(this);
        this.closestPositionFinder = new WizardClosestPositionFinder();
        if(WizardMagic.getInstance().isMagicEnabled()){
            new AbsorbCommand(this).execute();
            new HealCommand(this).execute();
        }
    }
    @Override
    public Character getInitial() {
        return 'W';
    }

    @Override
    public ImageIcon getImage() {
        return imageLoader.getImageForType(getType());
    }
    @Override
    public void die(){
        //System.out.println("Wizard died");
        super.die();
    }
    @Override
    public long waitingTiming() {
        long timeEnd = ThreadLocalRandom.current().nextInt(MIN_WAIT, MAX_WAIT);
        return timeEnd;
    }

    @Override
    public String toString() {
        return this.getType().toString() + "[" + this.getStatus().getId()+"]";
    }
}
