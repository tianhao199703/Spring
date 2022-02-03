package com.example.service;

import com.spring.Autowired;
import com.spring.Component;

import org.springframework.beans.factory.BeanNameAware;

@Component("userService")
public class UserService implements BeanNameAware {
    @Autowired
    private OrderService orderService;
    private String beanName;

    public void test() {
        System.out.println(orderService);
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
