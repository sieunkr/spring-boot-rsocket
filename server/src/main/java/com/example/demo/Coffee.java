package com.example.demo;

import lombok.Data;

public class Coffee {
    private String name;

    public Coffee(){
    }

    public Coffee(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
