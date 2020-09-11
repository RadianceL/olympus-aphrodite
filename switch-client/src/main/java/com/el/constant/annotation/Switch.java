package com.el.constant.annotation;

import java.lang.annotation.*;

/**
 * 表明该类是开关控制类 <br/>
 * since 2020/8/25
 *
 * @author eddie.lys
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Switch {

    String appName();
}
