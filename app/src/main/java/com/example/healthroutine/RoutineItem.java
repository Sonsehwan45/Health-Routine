package com.example.healthroutine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID; // 고유 ID 생성을 위해 추가

public class RoutineItem implements Serializable {
    private String id;                      // 루틴 고유 ID
    private String name;                    // 루틴 이름
    private ArrayList<ExerciseItem> exerciseList; // 루틴에 포함된 운동 목록

    public RoutineItem(String name, ArrayList<ExerciseItem> exerciseList) {
        this.id = UUID.randomUUID().toString(); // 랜덤한 고유 문자열 부여
        this.name = name;
        this.exerciseList = exerciseList;
    }

    //ID는 수정이 필요 없으니 getter만 작성
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ExerciseItem> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(ArrayList<ExerciseItem> exerciseList) {
        this.exerciseList = exerciseList;
    }
}