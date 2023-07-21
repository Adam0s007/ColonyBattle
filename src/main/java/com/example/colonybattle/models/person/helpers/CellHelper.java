package com.example.colonybattle.models.person.helpers;

import com.example.colonybattle.colors.ConsoleColor;
import com.example.colonybattle.utils.ColorConverter;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.utils.ThreadUtils;

import java.awt.*;

public class CellHelper {
    private Person person;
    private BoardRef boardRef;

    public CellHelper(Person person, BoardRef boardRef) {
        this.person = person;
        this.boardRef = boardRef;
    }

    public void updateLife() {
        setLife(person.getStatus().getHealth());
    }

    public void updateEnergy() {
        setEnergy(person.getStatus().getEnergy());
    }

    public void updateLifeAndEnergy() {
        updateLife();
        updateEnergy();
    }

    public void deathColor() {
        setColor(ConsoleColor.PURPLE, person.getPosition());
    }

    public Color getDeathColor() {
        return ColorConverter.convertColor(ConsoleColor.PURPLE);
    }

    public void resetCell(Point2d position) {
        setInitColor(position);
        setLife(0, position);
        setEnergy(0, position);
        setInitial(null, position);
        removeImageFromCell(position);
    }

    public void newCellAt(Point2d newPosition) {
        ConsoleColor consoleColor = person.getColony().getColor().getColor();
        setColor(consoleColor, newPosition);
        setLife(person.getStatus().getHealth(), newPosition);
        setEnergy(person.getStatus().getEnergy(), newPosition);
        setInitial(person.getInitial(), newPosition);
        addImageToCell(newPosition);
    }

    public void addImageToCell(Point2d position) {
        boardRef.getFrame().setImageAtPosition(position, person.getImage());
    }

    public void removeImageFromCell(Point2d position) {
        boardRef.getFrame().removeImageAtPosition(position);
    }

    public void healingColor() {
        changeColorTemporarily(ConsoleColor.BLUE);
    }

    public void energyEmitionColor() {
        changeColorTemporarily(ConsoleColor.BRIGHT_WHITE);
    }

    public void spawningColor() {
        Color color = new Color(255, 140, 0);
        Color oldColor = getCellColor(person.getPosition());
        setColor(color, person.getPosition());
        ThreadUtils.getInstance().pause(400);
        setColor(person.getColony().getColor().getColor(), person.getPosition());
        boardRef.getFrame().setBackground(person.getPosition(), oldColor);
    }

    private void setLife(int life, Point2d position) {
        boardRef.getFrame().setLifeAtPosition(position, life);
    }

    private void setEnergy(int energy, Point2d position) {
        boardRef.getFrame().setEnergyAtPosition(position, energy);
    }

    private void setInitial(Character initial, Point2d position) {
        boardRef.getFrame().setInitial(position, initial);
    }

    private void setColor(ConsoleColor color, Point2d position) {
        boardRef.getFrame().setColorAtPosition(position, ColorConverter.convertColor(color));
    }

    private void setColor(Color color, Point2d position) {
        boardRef.getFrame().setColorAtPosition(position, color);
    }

    private void setInitColor(Point2d position) {
        boardRef.getFrame().setInitColor(position);
    }

    private void changeColorTemporarily(ConsoleColor color) {
        ConsoleColor currentColor = person.getColony().getColor().getColor();
        setColor(color, person.getPosition());
        ThreadUtils.getInstance().pause(200);
        setColor(currentColor, person.getPosition());
    }


    private Color getCellColor(Point2d position) {
        return boardRef.getFrame().getColorAtPosition(position);
    }

    private void setLife(int life) {
        setLife(life, person.getPosition());
    }

    private void setEnergy(int energy) {
        setEnergy(energy, person.getPosition());
    }
}
