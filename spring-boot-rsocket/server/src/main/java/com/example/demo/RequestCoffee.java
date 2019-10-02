package com.example.demo;

import lombok.*;

public class RequestCoffee {
    private String name;

    public RequestCoffee(){

    }

    public RequestCoffee(String name){
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}