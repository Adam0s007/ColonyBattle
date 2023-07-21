package com.example.colonybattle.ui;

import java.awt.*;

class Bar {
    private Color color;
    private int maxValue;
    private int yPosition;
    private int width;

    Bar(Color color, int maxValue, int yPosition) {
        this.color = color;
        this.maxValue = maxValue;
        this.yPosition = yPosition;
    }

    void update(Cell cell) {
        if(cell.getPosition().getPerson() != null){
            int labelWidth = cell.getWidth();
            double valuePerPixel = maxValue / (double) labelWidth;
            int currentValue = (this.yPosition == 6) ? cell.getPosition().getPerson().getStatus().getEnergy() : cell.getPosition().getPerson().getStatus().getHealth();
            this.width = (int) (currentValue / valuePerPixel);
        }
        else this.width = 0;
    }

    void paint(Graphics g, Cell cell) {
        g.setColor(color);
        g.fillRect(3, yPosition, width, 4);
    }
}