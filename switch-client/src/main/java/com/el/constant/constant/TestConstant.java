package com.el.constant.constant;

import com.el.constant.annotation.Switch;
import com.el.constant.annotation.SwitchConstant;
import com.el.constant.security.Level;

/**
 * <br/>
 * since 2020/8/25
 *
 * @author eddie.lys
 */
@Switch(appName = "test")
public class TestConstant {

    @SwitchConstant(desc = "测试", security = Level.L3)
    public static final Boolean TEST = false;
}
