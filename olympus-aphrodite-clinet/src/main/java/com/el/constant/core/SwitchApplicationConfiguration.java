package com.el.constant.core;

import com.el.constant.annotation.Switch;
import com.el.constant.annotation.SwitchConstant;
import com.el.constant.exception.SwtichRuntimeException;
import com.el.constant.utils.PackageScanUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * switch系统配置 <br/>
 * since 2020/9/9
 *
 * @author eddie.lys
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SwitchApplicationConfiguration {

    private final SwitchServerConnector switchServerConnector;

    @PostConstruct
    public void init() {
        log.info("switch init process - start");
        String applicationBasePackage = SwitchApplicationSystem.getApplicationBasePackage();
        if (StringUtils.isBlank(applicationBasePackage)) {
            throw new SwtichRuntimeException("switch - applicationBasePackage is null please check !");
        }
        // 初始化 switch类
        initSwitchClass(applicationBasePackage);
    }

    /**
     * 启动初始化
     * @param basePackageName       spring boot application 启动包路径
     */
    private void initSwitchClass(String basePackageName) {
        // 查询spring boot application启动包路径下所有待遇Switch注解的类
        Set<String> targetClassNameSet = PackageScanUtils.findPackageClass(basePackageName, Switch.class);
        if (CollectionUtils.isEmpty(targetClassNameSet)) {
            return;
        }
        List<Class<?>> targetClassList = new ArrayList<>();
        // 转换成class
        targetClassNameSet.forEach(className -> {
            try {
                targetClassList.add(Class.forName(className));
            } catch (ClassNotFoundException e) {
                log.error("switch - init switch class error, skip class [{}]", className);
            }
        });
        // 对没个switch开关累进行处理
        targetClassList.forEach(this::accept);
    }

    private void accept(Class<?> targetClass) {
        // 获取类上的注解
        Switch annotation = targetClass.getAnnotation(Switch.class);
        // 获取类中所有成员变量
        Field[] allFields = FieldUtils.getAllFields(targetClass);
        // 过滤成员变量中含有SwitchConstant注解的成员
        List<Field> switchConstantList = Arrays.stream(allFields).filter(field -> Objects.nonNull(field.getAnnotation(SwitchConstant.class))).collect(Collectors.toList());
        // 注册成员到服务端
        switchServerConnector.registerAndInitSwitchField(annotation.appName(), targetClass.getName(), switchConstantList);
    }
}
