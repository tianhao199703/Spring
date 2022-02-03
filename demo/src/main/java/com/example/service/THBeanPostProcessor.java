package com.example.service;

import com.spring.BeanPostProcessor;
import com.spring.Component;

@Component
public class THBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessorBeforeInitialization(Object bean, String beanName) {
        System.out.println("before initialization");
        return bean;
    };

    @Override
    public Object postProcessorAfterInitialization(Object bean, String beanName) {
        System.out.println("after initialization");
        return bean;
    };
}
