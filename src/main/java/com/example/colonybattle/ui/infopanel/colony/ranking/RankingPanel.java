package com.example.colonybattle.ui.infopanel.colony.ranking;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.Person;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RankingPanel extends JPanel {
    private List<Colony> allColonies;

    public RankingPanel(List<Colony> allColonies) {
        this.allColonies = allColonies;
        this.setLayout(new GridLayout(1, 2));
        update();
    }

    public void update() {
        List<Person> people = allColonies.stream()
                .flatMap(colony -> colony.getPeople().stream())
                .collect(Collectors.toList());

        List<Person> topKills = people.stream()
                .sorted(Comparator.comparing(person -> person.getKills().getKills()*-1))
                .limit(5)
                .collect(Collectors.toList());

        List<Person> topAge = people.stream()
                .sorted(Comparator.comparing(person -> person.getStatus().getAge().negate()))
                .limit(5)
                .collect(Collectors.toList());

        removeAll();

        JPanel killsPanel = new JPanel();
        killsPanel.setLayout(new BoxLayout(killsPanel, BoxLayout.Y_AXIS));
        for (Person person : topKills) {
            JLabel label = new JLabel(person.toString() + " - Kills: " + person.getKills().getKills());
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            killsPanel.add(label);
        }

        JPanel agePanel = new JPanel();
        agePanel.setLayout(new BoxLayout(agePanel, BoxLayout.Y_AXIS));
        for (Person person : topAge) {
            JLabel label = new JLabel(person.toString() + " - Age: " + person.getStatus().getAge());
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            agePanel.add(label);
        }

        this.add(killsPanel);
        this.add(agePanel);

        revalidate();
        repaint();
    }
}
