package com.example;

import com.spring.THApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        THApplicationContext thApplicationContext = new THApplicationContext(AppConfig.class);
        Object userService = thApplicationContext.getBean("userService");

        System.out.println(userService);
    }
}
