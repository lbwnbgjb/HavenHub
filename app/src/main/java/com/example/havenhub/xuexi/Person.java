package com.example.havenhub.xuexi;

public class Person {
    private String name;
    private int age;
    private String miaoShu;


    public Person() {
    }

    public Person(String name, int age, String miaoShu) {
        this.name = name;
        this.age = age;
        this.miaoShu = miaoShu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getMiaoShu() {
        return miaoShu;
    }

    public void setMiaoShu(String miaoShu) {
        this.miaoShu = miaoShu;
    }
}
