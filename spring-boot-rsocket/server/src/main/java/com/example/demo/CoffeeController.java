package com.example.demo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CoffeeController {

    @MessageMapping("coffee")
    Coffee coffee(RequestCoffee request){
        return new Coffee(request.getName());
    }
}
