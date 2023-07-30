package com.example.colonybattle.ui.infopanel.colony.ranking;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.Person;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class RankingPanel extends JPanel {
    private List<Colony> allColonies;

    private Semaphore updatingFunc = new Semaphore(1);

    public RankingPanel(List<Colony> allColonies) {
        this.allColonies = allColonies;
        this.setLayout(new GridLayout(1, 2));
        update();
    }

    public void update() {
        if(!updatingFunc.tryAcquire()) return;
        List<Person> people = allColonies.stream()
                .flatMap(colony -> colony.getPeople().stream())
                .collect(Collectors.toList());

        List<Person> topKills = getTopKillsPeople(people);
        List<Person> topAge = getTopAgePeople(people);

        removeAll();

        JPanel killsPanel = createPanel(topKills, "Kills");
        JPanel agePanel = createPanel(topAge, "Age");

        this.add(killsPanel);
        this.add(agePanel);

        revalidate();
        repaint();
        updatingFunc.release();
    }

    private List<Person> getTopKillsPeople(List<Person> people) {
        return people.stream()
                .sorted(Comparator.comparing(person -> -person.getKills().getKills()))
                .limit(5)
                .collect(Collectors.toList());
    }

    private List<Person> getTopAgePeople(List<Person> people) {
        return people.stream()
                .sorted(Comparator.comparing(person -> person.getStatus().getAge().negate()))
                .limit(5)
                .collect(Collectors.toList());
    }

    private JPanel createPanel(List<Person> people, String attribute) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        populatePanel(panel, people, attribute);
        return panel;
    }

    private void populatePanel(JPanel panel, List<Person> people, String attribute) {
        for (Person person : people) {
            JLabel label = createPersonLabel(person, attribute);
            panel.add(label);
        }
    }

    private JLabel createPersonLabel(Person person, String attribute) {
        String text;
        if(attribute.equals("Kills")) {
            text = person.toString() + " - " + attribute + ": " + person.getKills().getKills();
        } else { // assuming attribute is "Age"
            text = person.toString() + " - " + attribute + ": " + person.getStatus().getAge();
        }
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }
}
