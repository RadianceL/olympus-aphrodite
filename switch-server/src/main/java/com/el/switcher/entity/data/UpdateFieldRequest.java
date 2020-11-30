package com.el.switcher.entity.data;

import lombok.Data;

/**
 * 更新对象 </br>
 * since 2020/11/30
 *
 * @author eddie
 */
@Data
public class UpdateFieldRequest {
    /**
     * 目标类型
     */
    private String targetType;
    /**
     * 目标更新值
     */
    private String targetFieldValue;
}
