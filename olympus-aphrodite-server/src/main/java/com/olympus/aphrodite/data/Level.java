package com.olympus.aphrodite.data;

/**
 * 安全级别 <br/>
 * since 2020/8/25
 *
 * @author eddie.lys
 */
public enum Level {
    /**
     * 安全级别 L1 最高安全级别，触发变更建议走审批
     */
    L1,
    /**
     * 安全级别 L2 次高安全级别，触发变更建议走审批
     */
    L2,
    /**
     * 安全级别 L3 中安全级别，触发变更建议观测两小时以上
     */
    L3,
    /**
     * 安全级别 L4 低安全级别，触发变更建议线上校验
     */
    L4
}
