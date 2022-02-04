package com.example.service;

import java.lang.reflect.Method;

import com.spring.BeanPostProcessor;
import com.spring.Component;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

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
        if(beanName.equals("userService")){
            Object proxyInstance = Proxy.newProxyInstance(THBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("proxy");
                    return method.invoke(bean, args);
                }
            });
            return proxyInstance;
        }
        return bean;
    };
}
