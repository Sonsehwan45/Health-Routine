package com.example.healthroutine;

import java.io.Serializable;
import java.util.ArrayList;

public class ExerciseItem implements Serializable {

    private String name;
    private ArrayList<OneSetItem> setList;

    public ExerciseItem() {
        this.name = "";
        this.setList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<OneSetItem> getSetList() {
        return setList;
    }
}
