package org.mybatis.spring;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;

public class THScanner extends ClassPathBeanDefinitionScanner{

    public THScanner(BeanDefinitionRegistry registry) {
        super(registry);
        //TODO Auto-generated constructor stub
    }
    
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        // TODO Auto-generated method stub
        return super.doScan(basePackages);
    }

    @Override
    protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
        // TODO Auto-generated method stub
        return metadataReader.getClassMetadata().isInterface();
    }
}
