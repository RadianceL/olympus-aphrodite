package com.olympus.aphrodite.data;

import lombok.Data;

/**
 * 开关字段描述 <br/>
 * since 2020/8/25
 *
 * @author eddie.lys
 */
@Data
public class SwitchFieldInfo {
    /**
     * 字段路径
     */
    private String fieldPath;
    /**
     * 字段名称
     */
    private String key;
    /**
     * 描述信息
     */
    private String desc;
    /**
     * 安全级别
     */
    private Level level;
    /**
     * 字段类型
     */
    private Class<?> classType;
    /**
     * 字段值
     */
    private Object value;
}
