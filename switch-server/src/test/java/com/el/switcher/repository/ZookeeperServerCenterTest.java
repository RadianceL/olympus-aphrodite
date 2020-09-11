package com.el.switcher.repository;

import com.el.switcher.data.Level;
import com.el.switcher.data.SwitchFieldInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <br/>
 * since 2020/9/9
 *
 * @author eddie.lys
 */
@SpringBootTest
class ZookeeperServerCenterTest {

    @Autowired
    private ZookeeperServerCenter zookeeperServerCenter;

    @Test
    void updateTargetField() {
        SwitchFieldInfo switchFieldInfo = new SwitchFieldInfo();
        switchFieldInfo.setDesc("测试");
        switchFieldInfo.setLevel(Level.L3);
        switchFieldInfo.setKey("test");
        switchFieldInfo.setValue(true);
        zookeeperServerCenter.updateTargetField("/test/com.el.constant.constant.TestConstant/TEST", switchFieldInfo);
    }
}