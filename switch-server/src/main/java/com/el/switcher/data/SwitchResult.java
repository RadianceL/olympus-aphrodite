package com.el.switcher.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标准返回 <br/>
 * since 2020/8/26
 *
 * @author eddie.lys
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwitchResult<T> {

    /**
     * 标准返回代码
     */
    private boolean success;

    /**
     * 系统码
     */
    private String code;

    /**
     * 业务参数返回
     */
    private T data;

    public static <T> SwitchResult<T> ofSuccess(T data) {
        SwitchResult<T> result = new SwitchResult<>();
        result.setCode("0000");
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

}
