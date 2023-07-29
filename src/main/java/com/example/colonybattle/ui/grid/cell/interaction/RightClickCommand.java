package com.example.colonybattle.ui.grid.cell.interaction;


import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.command.Command;
import com.example.colonybattle.ui.grid.cell.Cell;

import java.awt.event.MouseEvent;

public class RightClickCommand implements Command {
    private Cell cell;

    public RightClickCommand(Cell cell) {
        this.cell = cell;
    }

    @Override
    public void execute(MouseEvent e) {
        Person personRef = cell.getPosition().getPerson();
        if (personRef == null && cell.getPersonPanel().getPerson() != null) {
            cell.setNewTarget();
        }
    }
}
