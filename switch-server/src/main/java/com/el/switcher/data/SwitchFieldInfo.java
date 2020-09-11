package com.el.switcher.data;

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
     * 字段值
     */
    private Object value;
}
