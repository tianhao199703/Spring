package com.example.service;

import com.spring.Component;

import org.springframework.beans.factory.annotation.Autowired;

@Component("bService")
public class BService {
    @Autowired
    private AService aService;
}
