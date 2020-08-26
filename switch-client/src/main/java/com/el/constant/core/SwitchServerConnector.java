package com.el.constant.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.el.base.utils.web.HttpsClientUtil;
import com.el.constant.annotation.SwitchConstant;
import com.el.constant.data.SwitchFieldInfo;
import com.el.constant.data.SwitchRegisterDTO;
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

        try {
            String result = HttpsClientUtil.singletonHttpsPostRequest(serverAddr + REGISTER_SWITCH_URL + appName, null, JSON.toJSONString(switchRegisterDTO));

            JSONObject resultObj = JSON.parseObject(result);
            Boolean success = resultObj.getBoolean("success");
            if (!success) {
                log.error("switch - register field has error happen! result: [{}]", result);
            }
        } catch (Exception e) {
            throw new RuntimeException("switch - connection switch server error: init", e);
        }
    }

    public static String findAllSwitchDataByAppName(String serverAddr, String appName) {
        if (StringUtils.isBlank(serverAddr) || StringUtils.isBlank(appName)) {
            throw new IllegalArgumentException("switch - server address can not be null, please check ~");
        }
        try {
            return HttpsClientUtil.singletonHttpsGetRequest(serverAddr + ALL_SWITCH_URL + appName, null);
        } catch (Exception e) {
            throw new RuntimeException("switch - connection switch server error: init");
        }
    }
}
