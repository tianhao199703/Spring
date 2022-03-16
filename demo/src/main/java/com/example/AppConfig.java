package com.example;

import com.spring.ComponentScan;

import org.mybatis.spring.THImportBeanDefinitionRegistry;
import org.springframework.context.annotation.Import;

@ComponentScan("com.example.service")
@Import(THImportBeanDefinitionRegistry.class)
public class AppConfig {
    
}
