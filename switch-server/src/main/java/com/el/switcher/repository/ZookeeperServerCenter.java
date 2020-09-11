package com.el.switcher.repository;

import com.el.switcher.data.SwitchFieldInfo;
import com.el.zk.core.ZookeeperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void updateTargetField(String path, SwitchFieldInfo switchFieldInfo) {
        zookeeperRepository.setNodeData(path, switchFieldInfo);
    }
}
