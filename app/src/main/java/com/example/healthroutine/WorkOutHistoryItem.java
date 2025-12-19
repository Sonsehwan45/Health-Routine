package com.example.healthroutine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

// 운동기록을 저장할 데이터 구조(세트 완료여부까지 저장)
public class WorkOutHistoryItem implements Serializable {
    private String id;
    private String name;
    private String date;
    private ArrayList<ExerciseItem> workoutList;

    public WorkOutHistoryItem(String date,  String name, ArrayList<ExerciseItem> workoutList) {
        this.id = UUID.randomUUID().toString();
        this.date = date;
        this.name = name;
        this.workoutList = workoutList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<ExerciseItem> getWorkoutList() {
        return workoutList;
    }

    public void setWorkoutList(ArrayList<ExerciseItem> workoutList) {
        this.workoutList = workoutList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
