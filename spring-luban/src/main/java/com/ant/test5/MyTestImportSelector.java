package com.test1.ant.test5;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class MyTestImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableMyTest.class.getName()));
        String name = annotationAttributes.getString("name");
        boolean isAuto = annotationAttributes.getBoolean("isAuto");
        return new String[0];
    }
}
