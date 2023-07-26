package com.example.colonybattle.ui;

import javax.swing.*;
import javax.swing.border.Border;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.utils.InitialConventer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Cell extends JLabel {
    private Point2d position;
    private PersonPanel personPanel;
    private final Color INITIAL_BACKGROUND = new Color(15, 23, 51);
    private final Bar healthBar = new Bar(new Color(255, 194, 189), 20,3);
    private final Bar energyBar = new Bar(new Color(255, 255, 189), 20, 6);
    private ImageIcon image= null;
    private Border border;

    public Cell(int x, int y) {
        this.setLayout(new BorderLayout());
        this.position = new Point2d(x, y);
        this.setOpaque(true);
        initializeCell();
        addClickListener();
    }

    private void initializeCell() {
        setInitialCellProperties();
        initColor();
        setFont(new Font("Sans Serif", Font.BOLD, 7));
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setIconTextGap(-15);
    }

    private void setInitialCellProperties() {
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.BOTTOM);
        border = BorderFactory.createLineBorder(new Color(0,0,0,0), 2);
        this.setBorder(border);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        healthBar.paint(g, this);
        energyBar.paint(g, this);
    }

    private void addClickListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Person personRef = position.getPerson();
                if(personRef == null || (personPanel.getPerson() != null && personPanel.getPerson().equals(personRef))) return;
                if(personPanel.getPerson() != null) personPanel.getPerson().setBeingFocused(false);
                personRef.setBeingFocused(true);
                personPanel.setPerson(personRef);
            }
        });
    }

    public void updateHealthBar() {
        healthBar.update(this);
    }

    public void updateEnergyBar() {
        energyBar.update(this);
    }

    public void updateLife(int life) {
        updateHealthBar();
    }
    public void updateEnergy(int energy){
        updateEnergyBar();
    }

    public void updateInitial(Character initial) {
        String convertedInitial = InitialConventer.getInstance().convertInitial(initial);
        this.setText(convertedInitial != null ? convertedInitial : "");
    }

    public void setImageIcon(ImageIcon icon) {
        if (icon == null) {
            System.out.println("null image");
            removeImageIcon();
            return;
        }
        Image originalImage = icon.getImage();
        Image scaledImage = originalImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        this.image = new ImageIcon(scaledImage);
        this.setIcon(image);
    }

    public void removeImageIcon() {
        image = null;
        this.setIcon(image);
    }

    public void updateColors(Color color) {
        border = BorderFactory.createLineBorder(color, 2);
        if(position.getPerson() != null && position.getPerson().isBeingFocused()){
            this.border = BorderFactory.createLineBorder(position.getPerson().getFocusColor(), 2);
        }
        this.setBorder(border);
        this.setForeground(Color.WHITE);
        updateBackground(this.position);
    }

    public void updateBackground(Point2d position) {
        Color color = position.getColonyColor();
        this.setBackground((color != null ? color : INITIAL_BACKGROUND).darker());
        cursorChanger();
    }

    public Point2d getPosition() {
        return position;
    }

    public void setPosition(Point2d position) {
        this.position = position;
    }
    public void setPersonPanel(PersonPanel personPanel) {
        this.personPanel = personPanel;
    }
    public void initColor() {
        updateBackground(this.position);
        border = BorderFactory.createLineBorder(new Color(0,0,0,0), 2);
        this.setBorder(border);
    }



    public void cursorChanger(){
        if (position.getPerson() != null) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
}

