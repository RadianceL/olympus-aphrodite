package com.el.switcher.service;

import com.el.switcher.data.TargetPath;

/**
 * 核心数据服务 </br>
 * since 2020/11/26
 *
 * @author eddie
 */
public interface DataSourceCoreService {

    /**
     * 更新模板字段
     */
    boolean updateTargetField(TargetPath targetPath, Object targetValue);

}
