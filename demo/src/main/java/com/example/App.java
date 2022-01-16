package com.example;

import com.example.service.UserService;
import com.spring.THApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        THApplicationContext thApplicationContext = new THApplicationContext(AppConfig.class);
        UserService userService = (UserService) thApplicationContext.getBean("userService");

        System.out.println(userService);
        userService.test();
    }
}
