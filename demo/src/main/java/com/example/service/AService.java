package com.example.service;

import com.spring.Component;

import org.springframework.beans.factory.annotation.Autowired;

@Component("aService")
public class AService {
    @Autowired
    private BService bService;

    public void test(){
        System.out.print("Test for circular dependency!");
    }
}
