package com.el.constant.core;

import com.el.base.utils.collection.CollectionUtils;
import com.el.base.utils.scan.PackageScan;
import com.el.constant.annotation.Switch;
import com.el.constant.annotation.SwitchConstant;
import com.el.constant.controller.SwitchUpdateEndpoint;
import com.el.constant.utils.ConstantValueUpdate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <br/>
 * since 2020/8/25
 *
 * @author eddie.lys
 */
@Order
@Slf4j
public class EnableSwitchClientImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        String className = importingClassMetadata.getClassName();
        String basePackageName = className.substring(0, className.lastIndexOf("."));
        // 初始化 switch类
        initSwitchClass(basePackageName);
        return new String[]{SwitchUpdateEndpoint.class.getName()};
    }

    /**
     * 启动初始化
     * @param basePackageName       spring boot application 启动包路径
     */
    private void initSwitchClass(String basePackageName) {
        // 查询spring boot application启动包路径下所有待遇Switch注解的类
        Set<String> targetClassNameSet = PackageScan.findPackageClass(basePackageName, Switch.class);
        if (CollectionUtils.isEmpty(targetClassNameSet)) {
            return;
        }
        List<Class<?>> targetClassList = new ArrayList<>();
        // 转换成class
        targetClassNameSet.forEach(className -> {
            try {
                targetClassList.add(Class.forName(className));
            } catch (ClassNotFoundException e) {
                log.error("init switch class error, skip class [{}]", className);
            }
        });
        // 对没个switch开关累进行处理
        targetClassList.forEach(EnableSwitchClientImportSelector::accept);
    }


    private static void accept(Class<?> targetClass) {
        // 获取类上的注解
        Switch annotation = targetClass.getAnnotation(Switch.class);
        // 获取类中所有成员变量
        Field[] allFields = FieldUtils.getAllFields(targetClass);
        // 过滤成员变量中含有SwitchConstant注解的成员
        List<Field> switchConstantList = Arrays.stream(allFields).filter(field -> Objects.nonNull(field.getAnnotation(SwitchConstant.class))).collect(Collectors.toList());
        // 注册成员到服务端
        SwitchServerConnector.registerSwitchField(annotation.serverAddr(), annotation.appName(), targetClass.getName(), switchConstantList);
        // 获取服务端当前app下全量配置信息
        Map<String, Object> allSwitchDataByAppNameMap = SwitchServerConnector.findAllSwitchDataByAppName(annotation.serverAddr(), annotation.appName());
        // 使用服务端信息初始化项目中的成员
        initSwitchField(switchConstantList, allSwitchDataByAppNameMap);
    }

    private static void initSwitchField(List<Field> switchConstantList, Map<String, Object> allSwitchDataByAppName) {
        if (CollectionUtils.isEmpty(switchConstantList) || CollectionUtils.isEmpty(allSwitchDataByAppName)) {
            return;
        }
        switchConstantList.forEach(field -> {
            Object serverFieldValue = allSwitchDataByAppName.get(field.getName());
            if (Objects.nonNull(serverFieldValue)) {
                boolean b = ConstantValueUpdate.updateTargetBoolean(field, serverFieldValue);
            }
        });
    }
}
