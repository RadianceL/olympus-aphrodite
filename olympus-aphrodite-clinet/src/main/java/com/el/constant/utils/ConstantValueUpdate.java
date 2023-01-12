package com.el.constant.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

/**
 * 常量更新工具 <br/>
 * since 2020/8/25
 *
 * @author eddie.lys
 */
@Slf4j
public class ConstantValueUpdate {

    /**
     * 更新常量字段
     * @param targetField   目标字段
     * @param value         服务端数据
     * @return              成功 || 失败
     */
    public static boolean updateTargetBoolean(Field targetField, Object value) {
        try{
            // 去掉final对字段的影响
            FieldUtils.removeFinalModifier(targetField);
            targetField.setAccessible(true);
            FieldUtils.writeStaticField(targetField, value);
            return true;
        }catch (Throwable throwable) {
            log.error("switch - update static target field error happen", throwable);
            return false;
        }
    }

}
