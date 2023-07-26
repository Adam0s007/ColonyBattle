package com.example.colonybattle.ui;

import com.example.colonybattle.board.position.Point2d;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class GridPanel extends JPanel {
    private Cell[][] grid;
    private final PersonPanel personPanel;
    public GridPanel(int gridSize, PersonPanel personPanel) {
        setPreferredSize(new Dimension(800, 800));
        this.setLayout(new GridLayout(gridSize, gridSize,0,0));
        this.personPanel = personPanel;
        grid = new Cell[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = new Cell(i, j);
                grid[i][j].setPersonPanel(personPanel);
                this.add(grid[i][j]);
            }
        }
    }

    public Cell getCellAtPosition(Point2d position) {
        if(position == null) return null;
        return grid[position.getX()][position.getY()];
    }
    // Metoda do ustawiania koloru komórki na podstawie pozycji
    public void setColorAtPosition(Point2d position, Color color) {
        if(position == null) return;
        grid[position.getX()][position.getY()].updateColors(color);
    }


    public void setInitColor(Point2d position){
        if(position == null) return;
        grid[position.getX()][position.getY()].initColor();
    }

    // Metoda do ustawiania liczby żyć na danej pozycji
    public void setLifeAtPosition(Point2d position, int life) {
        if(position == null) return;
        grid[position.getX()][position.getY()].updateLife(life);
    }
    public void  setEnergyAtPosition(Point2d position, int energy){
        if(position == null) return;
        grid[position.getX()][position.getY()].updateEnergy(energy);
    }

    public void setInitial(Point2d position, Character initial){
        if(position == null) return;
        grid[position.getX()][position.getY()].updateInitial(initial);
    }

    // Metoda do ustawiania obrazka na danej pozycji
    // Metoda do ustawiania obrazka na danej pozycji
    public void setImageAtPosition(Point2d position, ImageIcon icon) {
        //System.out.println(icon == null);
        if(position == null) return;
        grid[position.getX()][position.getY()].setImageIcon(icon);
    }

    // Metoda do usuwania obrazka z danej pozycji
    public void removeImageAtPosition(Point2d position) {
        if(position == null) return;
        grid[position.getX()][position.getY()].removeImageIcon();
    }

    //
    public void setPositionReferences(Map<String, Point2d> positionReferences){
        positionReferences.entrySet().stream()
                .forEach(entry -> {
                    Point2d position = entry.getValue();
                    if(position != null)
                        grid[position.getX()][position.getY()].setPosition(position);
                });
    }
    public void setBackground(Point2d position, Color color){
        if(position == null) return;
        grid[position.getX()][position.getY()].setBackground(color);
    }
    public Color getColorAtPosition(Point2d position){
        if(position == null) return null;
        return grid[position.getX()][position.getY()].getBackground();
    }
}
