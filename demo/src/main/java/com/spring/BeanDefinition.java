package com.spring;

import com.example.common.CommonConstant;

import org.springframework.beans.factory.config.Scope;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeanDefinition {
    private Class clazz;
    private String scope;

    public boolean isSingleton(){
        return scope.equals(CommonConstant.SINGLETON);
    }
}
