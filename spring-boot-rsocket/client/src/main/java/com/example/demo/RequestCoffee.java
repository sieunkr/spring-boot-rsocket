package com.example.demo;

import lombok.Data;

@Data
public class RequestCoffee {
    private String name;

    public RequestCoffee(){

    }

    public RequestCoffee(String name){
        this.name = name;
    }

}