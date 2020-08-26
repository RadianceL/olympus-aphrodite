package com.el.constant.annotation;

import com.el.constant.core.EnableSwitchClientImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启swtich自动配置 <br/>
 * since 2020/8/25
 *
 * @author eddie.lys
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(EnableSwitchClientImportSelector.class)
public @interface EnableSwitchAutoConfiguration {
}
