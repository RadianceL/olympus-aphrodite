package com.el.switcher.controller;

import com.el.base.utils.support.exception.ExtendRuntimeException;
import com.el.switcher.data.SwitchFieldInfo;
import com.el.switcher.data.TargetPath;
import com.el.switcher.entity.Response;
import com.el.switcher.repository.ZookeeperServerCenter;
import com.el.switcher.service.DataSourceCoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 核心接口 <br/>
 * since 2020/9/11
 *
 * @author eddie.lys
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SwitchMainController {

    private final ZookeeperServerCenter zookeeperServerCenter;

    private final DataSourceCoreService dataSourceCoreService;

    /**
     * 查询switch监管的所有应用
     * @return  服务下所有应用
     */
    @GetMapping("/switch/data/application/list")
    public Response<List<String>> applicationList() {
        return Response.ofSuccess(zookeeperServerCenter.listAllApplication());
    }

    /**
     * 获取应用下所有字段信息
     * @param application       应用名称
     * @return                  应用下所有字段
     */
    @GetMapping("/switch/data/{application}/info")
    public Response<Map<String, List<String>>> showCurrentSwitchData(@PathVariable String application) {
        return Response.ofSuccess(zookeeperServerCenter.listAllConstantClassByApplication(application));
    }

    @GetMapping("/switch/data/all/info")
    public Response<Map<String, Map<String, List<SwitchFieldInfo>>>> showAllSwitchFieldData() {
        return Response.ofSuccess(zookeeperServerCenter.listAllSwitchFieldData());
    }

    @PostMapping("/switch/data/{application}/{targetClass}/{targetField}")
    public Response<Boolean> updateField(@PathVariable String application, @PathVariable String targetClass, @PathVariable String targetField, String targetType, String targetFieldValue){
        TargetPath targetPath = TargetPath.ofPath(application, targetClass, targetField);
        if (StringUtils.isBlank(targetType)) {
            throw new ExtendRuntimeException("target class type can't be blank");
        }
        boolean isUpdateSuccess = dataSourceCoreService.updateTargetField(targetPath, targetType, targetFieldValue);
        // 返回更新成功的机器数量及ip，失败的机器数量及IP
        return Response.ofSuccess(isUpdateSuccess);
    }

}
