package com.gedehua.POJO;

public class Student {
    private String name;
    private String No;
    private float score;
    public  Student(){};

    public Student(String name, String no, float score) {
        this.name = name;
        No = no;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", No='" + No + '\'' +
                ", score=" + score +
                '}';
    }
}
