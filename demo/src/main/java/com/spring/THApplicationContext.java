package com.spring;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.common.CommonConstant;

import org.springframework.beans.factory.BeanNameAware;

public class THApplicationContext {
    private Class configClass;
    private ConcurrentHashMap<String, Object> singletonObjectPool = new ConcurrentHashMap<>();// 单例池
    private ConcurrentHashMap<String, Object> level3Cache = new ConcurrentHashMap<>();// 三级缓存map
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public THApplicationContext(Class configClass) {
        this.configClass = configClass;
        // 解析配置类
        // ComponentScan注解 -->扫描路径-->扫描-->BeanDefinitionMap
        scan(configClass);
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            if (entry.getValue().isSingleton()) {
                Object obj = createBean(entry.getKey(), entry.getValue());
                singletonObjectPool.put(entry.getKey(), obj);
            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getClazz();
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            level3Cache.put(beanName, instance);
            // 依赖注入
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object obj = getBean(field.getName());
                    field.setAccessible(true);
                    field.set(instance, obj);
                }
            }
            // aware回调
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }
            // BeanPostProcessor
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessorBeforeInitialization(instance, beanName);
            }
            // 初始化
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }
            // BeanPostProcessor
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessorAfterInitialization(instance, beanName);
            }
            return instance;
        } catch (Exception e) {
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
                if (BeanPostProcessor.class.isAssignableFrom(targetClass)) {
                    BeanPostProcessor instance = (BeanPostProcessor) targetClass.getDeclaredConstructor().newInstance();
                    beanPostProcessorList.add(instance);
                }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String beanName) throws Exception {
        if (!beanDefinitionMap.containsKey(beanName)) {
            return level3Cache.get(beanName);
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition.isSingleton()) {
            return singletonObjectPool.get(beanName);
        } else {
            return createBean(beanName, beanDefinition);
        }
    }
}