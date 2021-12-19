package com.spring;

public class THApplicationContext{
    private Class configClass;

    public THApplicationContext(Class configClass){
        this.configClass = configClass;
        // 解析配置类
        // ComponentScan注解 -->扫描路径-->扫描
        
    }

    public Object getBean(String beanName){
        return null;
    }
}