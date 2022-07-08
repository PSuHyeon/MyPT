package com.example.testest;

public class Exercise {
    int id;
    String name; // 사람 이름 ex. 우다연
    String date; // 날짜 2022-07-08
    String type; // 운동 종류 유산소
    String exercise; // 운동 이름 달리기
    String time; // 운동별 시간 (분 단위) 60
    String number; // 횟수(1세트에 하는 횟수) 1
    String set; // 세트 1
    String weight; // 무게 0

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
