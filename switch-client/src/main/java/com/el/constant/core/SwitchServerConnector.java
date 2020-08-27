package com.el.constant.core;

import com.alibaba.fastjson.JSON;
import com.el.base.utils.web.HttpsClientUtil;
import com.el.constant.annotation.SwitchConstant;
import com.el.constant.data.SwitchFieldInfo;
import com.el.constant.data.SwitchRegisterDTO;
import com.el.constant.data.SwitchResult;
import com.el.constant.exception.SwtichRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 开关服务端链接器 <br/>
 * since 2020/8/25
 *
 * @author eddie.lys
 */
@Slf4j
public class SwitchServerConnector {

    private static final String ALL_SWITCH_URL = "/switch/data/find-all/";

    private static final String REGISTER_SWITCH_URL = "/switch/data/register/";

    /**
     * 注册Switch 属性
     * @param serverAddr    服务地址
     * @param appName       应用scope
     * @param className     类名称
     * @param fields        类成员属性
     */
    public static void registerSwitchField(String serverAddr,String appName, String className, List<Field> fields) {
        SwitchRegisterDTO switchRegisterDTO = new SwitchRegisterDTO();
        switchRegisterDTO.setAppName(appName);
        switchRegisterDTO.setClassName(className);
        List<SwitchFieldInfo> switchFieldInfos = fields.stream().filter(field -> Objects.nonNull(field.getAnnotation(SwitchConstant.class))
        ).map(field -> {
            SwitchConstant switchConstant = field.getAnnotation(SwitchConstant.class);
            SwitchFieldInfo switchFieldInfo = new SwitchFieldInfo();
            switchFieldInfo.setDesc(switchConstant.desc());
            switchFieldInfo.setLevel(switchConstant.security());
            try {
                switchFieldInfo.setValue(FieldUtils.readStaticField(field, true));
            } catch (IllegalAccessException e) {
                log.error("can not read target field: [{}], skip", field.getName());
            }
            return switchFieldInfo;
        }).collect(Collectors.toList());

        switchRegisterDTO.setFieldInfo(switchFieldInfos);
        String result;
        try {
            result = HttpsClientUtil.singletonHttpsPostRequest(serverAddr + REGISTER_SWITCH_URL + appName, null, JSON.toJSONString(switchRegisterDTO));
        } catch (Exception e) {
            log.error("switch - register field has error happen! request switch server error", e);
            throw new SwtichRuntimeException("switch - register field has error happen!", e);
        }
        SwitchResult<Void> switchResult = convert(result);
        if (!switchResult.isSuccess()) {
            log.error("switch - register field has error happen! result: [{}]", result);
            throw new SwtichRuntimeException("switch - register field has error happen!");
        }
    }

    public static <T> T findAllSwitchDataByAppName(String serverAddr, String appName) {
        if (StringUtils.isBlank(serverAddr) || StringUtils.isBlank(appName)) {
            throw new IllegalArgumentException("switch - server address can not be null, please check ~");
        }

        String result;
        try {
            result = HttpsClientUtil.singletonHttpsGetRequest(serverAddr + ALL_SWITCH_URL + appName, null);
        } catch (Exception e) {
            log.error("switch - connection switch server error: init switch server request error", e);
            throw new SwtichRuntimeException("switch - connection switch server error: init switch server request error");
        }

        SwitchResult<T> convert = convert(result);
        if (Objects.isNull(convert) || !convert.isSuccess()) {
            throw new SwtichRuntimeException("switch - connection switch server error: init switch server result is not success");
        }
        return convert.getData();
    }

    /**
     * 由开发者保证此处不会抛出异常
     * @param result        switch server result data
     * @param <T>           SwitchResult.data type
     * @return              {@link SwitchResult}
     */
    @SuppressWarnings("unchecked")
    private static <T> SwitchResult<T> convert(String result) {
        return JSON.parseObject(result, SwitchResult.class);
    }
}
