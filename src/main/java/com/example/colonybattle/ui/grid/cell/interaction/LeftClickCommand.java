package com.example.colonybattle.ui.grid.cell.interaction;

import com.example.colonybattle.command.Command;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.ui.grid.cell.Cell;
import com.example.colonybattle.ui.infopanel.person.PersonPanel;

import java.awt.event.MouseEvent;

public class LeftClickCommand implements Command {

    private Cell cell;

    public LeftClickCommand(Cell cell) {
        this.cell = cell;
    }
    @Override
    public void execute(MouseEvent e) {
        Person personRef = cell.getPosition().getPerson();
        PersonPanel personPanel = cell.getPersonPanel();
        if (personRef != null && !(personPanel.getPerson() != null && personPanel.getPerson().equals(personRef))) {
            cell.focusOnPerson(personRef);
        }
    }
}