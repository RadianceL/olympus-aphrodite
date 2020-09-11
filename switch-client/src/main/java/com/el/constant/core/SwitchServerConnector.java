package com.el.constant.core;

import com.el.constant.annotation.SwitchConstant;
import com.el.constant.data.SwitchFieldInfo;
import com.el.constant.utils.ConstantValueUpdate;
import com.el.zk.core.ZookeeperRepository;
import com.el.zk.serialize.SerializingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.zookeeper.AddWatchMode;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
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
     * @param appName       应用scope
     * @param className     类名称
     * @param fields        类成员属性
     */
    public void registerAndInitSwitchField(String appName, String className, List<Field> fields) {
        String path = "/".concat(appName).concat("/").concat(className);
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
            if (zookeeperRepository.isExistNode(k)) {
                byte[] nodeData = zookeeperRepository.getNodeData(k);
                SwitchFieldInfo switchFieldInfo = SerializingUtil.deserialize(nodeData, SwitchFieldInfo.class);
                ConstantValueUpdate.updateTargetBoolean(v, switchFieldInfo.getValue());
                return;
            }

            SwitchConstant switchConstant = v.getAnnotation(SwitchConstant.class);
            SwitchFieldInfo switchFieldInfo = new SwitchFieldInfo();
            switchFieldInfo.setDesc(switchConstant.desc());
            switchFieldInfo.setLevel(switchConstant.security());
            switchFieldInfo.setKey(v.getName());
            try {
                switchFieldInfo.setValue(FieldUtils.readStaticField(v, true));
            } catch (IllegalAccessException e) {
                log.error("switch - can not read target field: [{}], skip: register error", v.getName());
            }
            zookeeperRepository.createNode(CreateMode.PERSISTENT, k, switchFieldInfo);
        });

        // watch更优雅的方案目前没找到 直接使用zk的watch方案
        zookeeperRepository.getClient().getZookeeperClient().getZooKeeper().addWatch("/switch", watchedEvent -> {
            if (Watcher.Event.EventType.NodeDataChanged.equals(watchedEvent.getType())) {
                try {
                    byte[] nodeData = zookeeperRepository.getNodeData(watchedEvent.getPath().replaceFirst("/switch", ""));
                    SwitchNodeChangeListenerHolder.update(new ChildData(watchedEvent.getPath(), null, nodeData));
                }catch (Throwable throwable) {
                    log.error("switch - update event error, data path: [{}]", watchedEvent.getPath());
                }
            }
        }, AddWatchMode.PERSISTENT_RECURSIVE);
    }
}
