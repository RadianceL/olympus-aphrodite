package com.el.constant.core;

import com.el.constant.annotation.SwitchConstant;
import com.el.constant.data.SwitchFieldInfo;
import com.el.constant.utils.ConstantValueUpdate;
import com.el.zk.core.ZookeeperRepository;
import com.el.zk.serialize.SerializingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 开关服务端链接器 <br/>
 * since 2020/8/25
 *
 * @author eddie.lys
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SwitchServerConnector {

    private final ZookeeperRepository zookeeperRepository;

    /**
     * 注册Switch 属性
     * @param classDesc     类描述
     * @param className     类名称
     * @param fields        类成员属性
     */
    public void registerAndInitSwitchField(String classDesc, String className, List<Field> fields) {
        String path = "/".concat(className);
        fields.stream()
                .filter(field -> Objects.nonNull(field.getAnnotation(SwitchConstant.class)))
                .forEach(field -> SwitchApplicationSystem.registerSwitchFieldCache(path, field));
        try {
            initZookeeperFieldPath();
        } catch (Exception e) {
            log.error("switch - init switch field data error");
        }
    }

    private void initZookeeperFieldPath() throws Exception {
        // 初始化所有switch字段
        Map<String, Field> switchFieldCache = SwitchApplicationSystem.getSwitchFieldCache();
        switchFieldCache.forEach((k, v) -> {
            // 如果数据存在，及获取zk结果覆盖本地数据
            if (zookeeperRepository.isExistNode(k)) {
                byte[] nodeData = zookeeperRepository.getNodeData(k);
                SwitchFieldInfo switchFieldInfo = SerializingUtil.deserialize(nodeData, SwitchFieldInfo.class);
                ConstantValueUpdate.updateTargetBoolean(v, switchFieldInfo.getValue());
                return;
            }
            // 如果zk中没有该字段数据， 则在zk中创建数据
            SwitchConstant switchConstant = v.getAnnotation(SwitchConstant.class);
            SwitchFieldInfo switchFieldInfo = new SwitchFieldInfo();
            switchFieldInfo.setKey(v.getName());
            switchFieldInfo.setClassType(v.getType());
            switchFieldInfo.setDesc(switchConstant.desc());
            switchFieldInfo.setLevel(switchConstant.security());
            try {
                switchFieldInfo.setValue(FieldUtils.readStaticField(v, true));
            } catch (IllegalAccessException e) {
                log.error("switch - can not read target field: [{}], skip: register error", v.getName());
            }
            zookeeperRepository.createNode(CreateMode.PERSISTENT, k, switchFieldInfo);
        });

        // watch更优雅的方案目前没找到 直接使用zk的watch方案
        zookeeperRepository.startRootWatcherForUpdate(SwitchNodeChangeListenerHolder::update);
    }
}
