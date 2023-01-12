package com.olympus.aphrodite.service;

import com.olympus.aphrodite.data.TargetPath;

/**
 * 核心数据服务 </br>
 * since 2020/11/26
 *
 * @author eddie
 */
public interface DataSourceCoreService {


    /**
     * 更新模板字段
     * @param targetPath    目标路径
     * @param classType     修改的目标类
     * @param targetValue   目标值
     * @return              是否更新成功
     */
    boolean updateTargetField(TargetPath targetPath, String classType, String targetValue);

}
