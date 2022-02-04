package com.example;

import com.example.service.UserInterface;
import com.spring.THApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        THApplicationContext thApplicationContext = new THApplicationContext(AppConfig.class);
        UserInterface userService = (UserInterface) thApplicationContext.getBean("userService");

        System.out.println(userService);
        userService.test();
    }
}
