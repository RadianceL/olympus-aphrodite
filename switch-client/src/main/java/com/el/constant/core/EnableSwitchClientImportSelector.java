package com.el.constant.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

/**
 * <br/>
 * since 2020/8/25
 *
 * @author eddie.lys
 */
@Order
@Slf4j
public class EnableSwitchClientImportSelector implements ImportSelector {

    @NonNull
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        String className = importingClassMetadata.getClassName();
        SwitchApplicationSystem.setApplicationBasePackage(className.substring(0, className.lastIndexOf(".")));

        return new String[]{SwitchApplicationConfiguration.class.getName()};
    }
}
