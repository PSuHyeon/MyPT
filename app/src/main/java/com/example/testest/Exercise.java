package com.example.testest;

public class Exercise {
    public void setCurrent(String current) {
        this.current = current;
    }

    String id;
    String name; // 사람 이름 ex. 우다연
    String date; // 날짜 2022-07-08
    String type; // 운동 종류 유산소
    String exercise; // 운동 이름 달리기
    String time; // 운동별 시간 (분 단위) 60
    String number; // 횟수(1세트에 하는 횟수) 1
    String sett; // 세트 1
    String weight; // 무게 0
    String current;

    public Exercise(String current) {
        this.current = current;
    }

    public String getCurrent() {
        return current;
    }

    public Exercise(String id, String name, String date, String type, String exercise, String time, String number, String sett, String weight, String current) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.type = type;
        this.exercise = exercise;
        this.time = time;
        this.number = number;
        this.sett = sett;
        this.weight = weight;
        this.current = current;
    }

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
        return sett;
    }

    public void setSet(String set) {
        this.sett = set;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
