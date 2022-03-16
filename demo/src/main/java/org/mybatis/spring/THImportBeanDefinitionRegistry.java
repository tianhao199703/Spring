package org.mybatis.spring;

import java.io.IOException;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class THImportBeanDefinitionRegistry implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // TODO Auto-generated method stub
        String path = "com.example.userMapper";
        THScanner thScanner = new THScanner(registry);
        thScanner.addIncludeFilter(new TypeFilter() {

            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
                    throws IOException {
                // 不需要加Compoent注解
                return true;
            }

        });
        thScanner.doScan(path);
    }

}
