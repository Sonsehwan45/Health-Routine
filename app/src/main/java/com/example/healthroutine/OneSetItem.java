package com.example.healthroutine;

import java.io.Serializable;

public class OneSetItem implements Serializable {
    private String weight;
    private String count;
    private String restTime;
    private boolean isCompleted;

    // 빈 세트 추가용 기본 생성자
    public OneSetItem() {
        this.weight = "";
        this.count = "";
        this.restTime = "";
        this.isCompleted = false;
    }

    // 데이터가 있는 상태로 생성할 때
    public OneSetItem(String weight, String count, String restTime) {
        this.weight = weight;
        this.count = count;
        this.restTime = restTime;
        this.isCompleted = false;
    }

    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }

    public String getCount() { return count; }
    public void setCount(String count) { this.count = count; }

    public String getRestTime() { return restTime; }
    public void setRestTime(String restTime) { this.restTime = restTime; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}
