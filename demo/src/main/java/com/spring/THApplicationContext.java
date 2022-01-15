package com.spring;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class THApplicationContext {
    private Class configClass;
    private ConcurrentHashMap<String, Object> singletonObjectPool = new ConcurrentHashMap<>();// 单例池

    public THApplicationContext(Class configClass) {
        this.configClass = configClass;
        // 解析配置类
        // ComponentScan注解 -->扫描路径-->扫描
        ComponentScan componentScanAnnotation = (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
        String path = componentScanAnnotation.value();// 扫描
        System.out.println(path);

        // 扫描
        ClassLoader classLoader = THApplicationContext.class.getClassLoader();
        URL resources = classLoader.getResource(path.replace(".", "\\"));
        File resourceFile = new File(resources.getFile());
        if (resourceFile.isDirectory()) {
            File[] files = resourceFile.listFiles();
            for (File classFile : files) {
                String classFileName = classFile.getName();
                if (!classFileName.endsWith(".class")) {
                    continue;
                }
                String targetName = path + "." + classFileName.substring(0, classFileName.indexOf(".class"));
                classLoad(classLoader, targetName);
            }
        }

    }

    private void classLoad(ClassLoader classLoader, String targetName) {
        try {
            Class<?> targetClass = classLoader.loadClass(targetName);
            if (targetClass.isAnnotationPresent(Component.class)) {

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String beanName) {
        return null;
    }
}