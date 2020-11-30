package com.el.switcher.entity;

import com.el.switcher.entity.common.ResponseCodeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回
 * <p>
 * 2019-08-10
 *
 * @author eddie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    /**
     * 标准返回代码
     */
    private String code;

    /**
     * 系统信息
     */
    private boolean success;

    /**
     * 业务参数返回
     */
    private T value;

    /**
     * 无效的登录信息
     */
    public static final Response<String> INVALID_TOKEN = new Response<>(ResponseCodeConstants.INVALID_TOKEN_CODE_9999, false, "无效的登录信息");

    /**
     * 错误的用户名或密码
     */
    public static final Response<String> INVALID_USERNAME_PASSWORD = new Response<>(ResponseCodeConstants.INVALID_USERNAME_PASSWORD_9001, false, "错误的用户名或密码");

    /**
     * 参数缺失 不提示缺失字段
     */
    public static final Response<String> INVALID_MISSING_FIELD_PARAMETER = new Response<>(ResponseCodeConstants.INVALID_FIELD_PARAM_2002, false, "缺少必要参数");


    public Response(String resultCode, Boolean success, String message) {
        this.code = resultCode;
        this.success = success;
    }

    public static <T> Response<T> ofSuccess(String code, String message, T value) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setSuccess(true);
        response.setValue(value);
        return response;
    }

    public static <T> Response<T> ofSuccess(T value) {
        Response<T> response = new Response<>();
        response.setCode(ResponseCodeConstants.STATE_OK_0000);
        response.setSuccess(ResponseCodeConstants.STATE_OK_TYPE);
        response.setValue(value);
        return response;
    }

    public static <T> Response<T> ofGlobalError(T value) {
        Response<T> response = new Response<>();
        response.setCode(ResponseCodeConstants.GLOBAL_EXCEPTION_COMMON);
        response.setSuccess(ResponseCodeConstants.STATE_ERROR_TYPE);
        response.setValue(value);
        return response;
    }

    public static <T> Response<T> ofError(T value) {
        Response<T> response = new Response<>();
        response.setCode("code");
        response.setSuccess(ResponseCodeConstants.STATE_ERROR_TYPE);
        response.setValue(value);
        return response;
    }

    public static <T> Response<T> ofError(String code, T value) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setSuccess(ResponseCodeConstants.STATE_ERROR_TYPE);
        response.setValue(value);
        return response;
    }
}