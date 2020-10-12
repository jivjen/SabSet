package com.example.test1;

public class User {
    public String getName() {
        return name;
    }

    public User(){

    }
    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String name;
    public String number;

    public User(String number, String name){
        this.name = name;
        this.number = number;
    }
}
