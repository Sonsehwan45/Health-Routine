package com.example.healthroutine;

import java.util.ArrayList;

public class ExerciseItem {
    private String name;
    private ArrayList<OneSetItem> setList;

    public ExerciseItem(){
        this.name = "";
        this.setList = new ArrayList<>();
        this.setList.add(new OneSetItem());
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ArrayList<OneSetItem> getSetList() { return setList; }
}
