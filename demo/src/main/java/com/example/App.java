package com.example;

import com.example.service.AService;
import com.example.service.UserInterface;
import com.example.service.UserService;
import com.spring.THApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        THApplicationContext thApplicationContext = new THApplicationContext(AppConfig.class);
        AService aService = (AService) thApplicationContext.getBean("aService");
        aService.test();
        UserInterface userService = (UserInterface) thApplicationContext.getBean("userService");

        System.out.println(userService);
        userService.test();

        UserService userService1 = (UserService) thApplicationContext.getBean("userService");
        userService1.testMabatis();
    }
}
