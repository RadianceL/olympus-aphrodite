package com.el.constant.annotation;

import com.el.constant.security.Level;

import java.lang.annotation.*;

/**
 * 表明该类是开关控制类 <br/>
 * since 2020/8/25
 *
 * @author eddie.lys
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SwitchConstant {

    /**
     * 常量描述
     * @return  该字段的作用
     */
    String desc();

    /**
     * 变更安全级别
     * @return  {@link Level}
     */
    Level security() default Level.L4;
    
}
