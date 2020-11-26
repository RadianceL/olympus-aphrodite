package com.el.switcher.repository;

import com.el.switcher.data.SwitchFieldInfo;
import com.el.switcher.data.TargetPath;
import com.el.zk.core.ZookeeperRepository;
import com.el.zk.serialize.SerializingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * zk控制器 <br/>
 * since 2020/9/9
 *
 * @author eddie.lys
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ZookeeperServerCenter {

    private final ZookeeperRepository zookeeperRepository;

    public SwitchFieldInfo verifyDataClassType(TargetPath targetPath, Object targetValue) {
        byte[] nodeData = zookeeperRepository.getNodeData(targetPath.getPath());
        SwitchFieldInfo sourceFieldData = SerializingUtil.deserialize(nodeData, SwitchFieldInfo.class);
        Class<?> targetClassType = targetValue.getClass();
        Class<?> sourceClassType = sourceFieldData.getClassType();
        if (!targetClassType.equals(sourceClassType)) {
            throw new RuntimeException("更新数据类型与目标类型不一致，source: [ " + sourceClassType.getName() + " ]，target: [ " + targetClassType.getName() + " ]");
        }
        sourceFieldData.setValue(targetValue);
        return sourceFieldData;
    }

    public void updateTargetField(TargetPath targetPath, SwitchFieldInfo switchFieldInfo) {
        zookeeperRepository.setNodeData(targetPath.getPath(), switchFieldInfo);
    }

    public Map<String, Map<String, List<SwitchFieldInfo>>> listAllSwitchFieldData() {
        Map<String, Map<String, List<SwitchFieldInfo>>> switchFieldMap = new HashMap<>(8);
        Map<String, List<String>> constantClassMap = listAllConstantClassByApplication();
        if (CollectionUtils.isEmpty(constantClassMap)) {
            return switchFieldMap;
        }
        Set<String> applications = constantClassMap.keySet();
        applications.forEach(application -> {
            Map<String, List<SwitchFieldInfo>> applicationSwitchFieldMap = new HashMap<>(16);
            List<String> constantClasses = constantClassMap.get(application);
            // 如果应用内不包含switch常量类， 添加应用名称后退出
            if (CollectionUtils.isEmpty(constantClasses)) {
                switchFieldMap.put(application, null);
                return;
            }

            // 循环常量类中的switch常量字段
            constantClasses.forEach(constantsClass -> {
                String path = "/".concat(application).concat("/").concat(constantsClass);
                List<String> constantFields = zookeeperRepository.getChildren(path);
                // 如果该类常量字段为空的话，添加常量字段后退出
                if (CollectionUtils.isEmpty(constantFields)) {
                    applicationSwitchFieldMap.put(constantsClass, null);
                    switchFieldMap.put(application, applicationSwitchFieldMap);
                    return;
                }

                List<SwitchFieldInfo> switchFieldInfos = new ArrayList<>();
                for (String constantField : constantFields) {
                    String dataPath = path.concat("/").concat(constantField);
                    byte[] nodeData = zookeeperRepository.getNodeData(dataPath);

                    SwitchFieldInfo switchFieldInfo = SerializingUtil.deserialize(nodeData, SwitchFieldInfo.class);
                    switchFieldInfos.add(switchFieldInfo);
                }

                applicationSwitchFieldMap.put(constantsClass, switchFieldInfos);
            });
            switchFieldMap.put(application, applicationSwitchFieldMap);
        });

        return switchFieldMap;
    }

    /**
     * @return Map - 应用名称, 应用内常量类列表
     */
    public Map<String, List<String>> listAllConstantClassByApplication() {
        Map<String, List<String>> constantsMap = new HashMap<>(8);
        List<String> applications = listAllApplication();
        applications.forEach(application -> {
            List<String> constantsClassList = zookeeperRepository.getChildren("/".concat(application));
            constantsMap.put(application, constantsClassList);
        });
        return constantsMap;
    }

    public List<String> listAllApplication() {
        return zookeeperRepository.getChildren("/");
    }
}
