package com.spring;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.relation.RoleNotFoundException;

import com.example.common.CommonConstant;

public class THApplicationContext {
    private Class configClass;
    private ConcurrentHashMap<String, Object> singletonObjectPool = new ConcurrentHashMap<>();// 单例池
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public THApplicationContext(Class configClass) {
        this.configClass = configClass;
        // 解析配置类
        // ComponentScan注解 -->扫描路径-->扫描-->BeanDefinitionMap
        scan(configClass);
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            if (entry.getValue().isSingleton()) {
                Object o = createBean(entry.getValue());
                singletonObjectPool.put(entry.getKey(), o);
            }
        }
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getClass();
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void scan(Class configClass) {
        ComponentScan componentScanAnnotation = (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
        String path = componentScanAnnotation.value();// 扫描
        System.out.println(path);

        // 扫描
        ClassLoader classLoader = THApplicationContext.class.getClassLoader();
        URL resources = classLoader.getResource(path.replace(".", "/"));
        File resourceFile = new File(resources.getFile());
        if (resourceFile.isDirectory()) {
            File[] files = resourceFile.listFiles();
            for (File classFile : files) {
                String classFileName = classFile.getName();
                if (!classFileName.endsWith(CommonConstant.CLASS_SUFFIX)) {
                    continue;
                }
                String targetName = path + "."
                        + classFileName.substring(0, classFileName.indexOf(CommonConstant.CLASS_SUFFIX));
                classLoad(classLoader, targetName);
            }
        }
    }

    private void classLoad(ClassLoader classLoader, String targetName) {
        try {
            Class<?> targetClass = classLoader.loadClass(targetName);
            if (targetClass.isAnnotationPresent(Component.class)) {
                Component componentAnnotation = targetClass.getAnnotation(Component.class);
                String beanName = componentAnnotation.value();
                BeanDefinition beanDefinition = new BeanDefinition();
                beanDefinition.setClazz(targetClass);
                if (targetClass.isAnnotationPresent(Scope.class)) {
                    Scope scopeAnnotation = targetClass.getAnnotation(Scope.class);
                    beanDefinition.setScope(scopeAnnotation.value());
                } else {
                    beanDefinition.setScope(CommonConstant.SINGLETON);
                }
                beanDefinitionMap.put(beanName, beanDefinition);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String beanName) throws Exception {
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new Exception("do not exist bean");
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition.isSingleton()) {
            return singletonObjectPool.get(beanName);
        } else {
            return createBean(beanDefinition);
        }
    }
}