package com.el.constant.data;

import lombok.Data;

import java.util.List;

/**
 * 初始化注册传输对象 <br/>
 * since 2020/8/25
 *
 * @author eddie.lys
 */
@Data
public class SwitchRegisterDTO {

    private String appName;

    private String className;

    private List<SwitchFieldInfo> fieldInfo;
}
