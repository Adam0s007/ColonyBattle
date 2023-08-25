package com.example.colonybattle.colony;

import com.example.colonybattle.board.position.Point2d;
import lombok.Getter;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class FieldsManager {
    private Set<Point2d> fields;

    public FieldsManager(Set<Point2d> fields) {
        this.fields =  ConcurrentHashMap.newKeySet();;
        this.fields.addAll(fields);
    }
    public FieldsManager() {
        this.fields = ConcurrentHashMap.newKeySet();
    }

    public void addField(Point2d position) {
        fields.add(position);
    }

    public void removeField(Point2d position) {
        fields.remove(position);
    }

    public Point2d getFreeField() {
        return this.fields.stream()
                .filter(field -> field.getPerson() == null)
                .findAny().orElse(null);
    }
}

