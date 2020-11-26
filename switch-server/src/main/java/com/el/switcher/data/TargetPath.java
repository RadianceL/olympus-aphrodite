package com.el.switcher.data;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * 目标路径 </br>
 * since 2020/11/26
 *
 * @author eddie
 */
@Data
public class TargetPath {

    private static final String SEGMENTATION_SYMBOL = "/";

    private static Integer PATH_SIZE = 3;

    private String applicationName;

    private String targetClassName;

    private String targetFieldName;

    public String getPath() {
        return SEGMENTATION_SYMBOL.concat(this.applicationName).concat(SEGMENTATION_SYMBOL)
                .concat(this.targetClassName).concat(SEGMENTATION_SYMBOL).concat(this.targetFieldName);
    }

    public static TargetPath ofPath(String targetStringPath) {
        if (StringUtils.isBlank(targetStringPath)) {
            throw new RuntimeException("目标参数存在空 [targetPath]");
        }
        char c = targetStringPath.charAt(0);
        if (c == SEGMENTATION_SYMBOL.toCharArray()[0]) {
            targetStringPath = targetStringPath.replaceFirst("/", "");
        }
        String[] targetPathArray = targetStringPath.split(SEGMENTATION_SYMBOL);
        if (targetPathArray.length < PATH_SIZE) {
            throw new RuntimeException("目标参数存在空 [applicationName] || [targetClassName] || [targetFieldName]");
        }
        TargetPath targetPath = new TargetPath();
        targetPath.setApplicationName(targetPathArray[0]);
        targetPath.setTargetClassName(targetPathArray[1]);
        targetPath.setTargetFieldName(targetPathArray[2]);
        return targetPath;
    }

    public static TargetPath ofPath(String applicationName, String targetClassName, String targetFieldName) {
        if (StringUtils.isBlank(applicationName) || StringUtils.isBlank(targetClassName) || StringUtils.isBlank(targetFieldName)) {
            throw new RuntimeException("目标参数存在空 [applicationName] || [targetClassName] || [targetFieldName]");
        }
        TargetPath targetPath = new TargetPath();
        targetPath.setApplicationName(applicationName);
        targetPath.setTargetClassName(targetClassName);
        targetPath.setTargetFieldName(targetFieldName);
        return targetPath;
    }

}
