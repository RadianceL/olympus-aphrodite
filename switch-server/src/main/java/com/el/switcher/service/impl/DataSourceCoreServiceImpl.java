package com.el.switcher.service.impl;

import com.el.switcher.data.SwitchFieldInfo;
import com.el.switcher.data.TargetPath;
import com.el.switcher.entity.enumerate.ClassTypeMapEnum;
import com.el.switcher.repository.ZookeeperServerCenter;
import com.el.switcher.service.DataSourceCoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 核心数据服务 </br>
 * since 2020/11/26
 *
 * @author eddie
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DataSourceCoreServiceImpl implements DataSourceCoreService {

    private final ZookeeperServerCenter zookeeperServerCenter;

    // update 更新数据 创建数据节点/switch/update/${application}/updateVersion

    @Override
    public boolean updateTargetField(TargetPath targetPath, String classType, String targetValue) {
        try {
            ClassTypeMapEnum classMap = ClassTypeMapEnum.findClassMap(classType);
            Object targetObject = classMap.convertTo(targetValue);
            // 校验更新字段类型
            log.info("更新目标字段：{}, value: [{}]", targetPath.getPath(), targetObject);
            SwitchFieldInfo switchFieldInfo = zookeeperServerCenter.verifyDataClassType(targetPath, targetObject);
            // 实际更新字段 并创建数据节点/switch/update/${application}/updateVersion
            zookeeperServerCenter.updateTargetField(targetPath, switchFieldInfo);
            return true;
        }catch (Throwable e) {
            log.error("更新目标字段异常", e);
            return false;
        }
    }

}
