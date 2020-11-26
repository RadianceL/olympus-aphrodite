package com.el.switcher.service.impl;

import com.el.switcher.data.Level;
import com.el.switcher.data.SwitchFieldInfo;
import com.el.switcher.data.TargetPath;
import com.el.switcher.repository.ZookeeperServerCenter;
import com.el.switcher.service.DataSourceCoreService;
import com.el.zk.core.ZookeeperRepository;
import com.el.zk.serialize.SerializingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    private final ZookeeperRepository zookeeperRepository;

    // update 更新数据 创建数据节点/switch/update/${application}/updateVersion

    @Override
    public boolean updateTargetField(TargetPath targetPath, Object targetValue) {
        try {
            // 校验更新字段类型
            SwitchFieldInfo switchFieldInfo = zookeeperServerCenter.verifyDataClassType(targetPath, targetValue);
            // 实际更新字段 并创建数据节点/switch/update/${application}/updateVersion
            zookeeperServerCenter.updateTargetField(targetPath, switchFieldInfo);
            return true;
        }catch (Throwable e) {
            log.error("更新目标字段异常", e);
            return false;
        }
    }




}
