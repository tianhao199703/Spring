package com.example.service;

import com.example.userMapper.UserMapper;
import com.spring.Autowired;
import com.spring.Component;

@Component("userService")
public class UserService implements UserInterface {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserMapper userMapper;
    private String beanName;

    @Override
    public void test() {
        System.out.println(orderService);
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    public void testMabatis(){
        System.out.println(userMapper.selectById());
    }
}
