package com.el.constant.core;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Switch系统配置 <br/>
 * since 2020/9/9
 *
 * @author eddie.lys
 */
public class SwitchApplicationSystem {

    private static final Map<String, String> CONFIG = new ConcurrentHashMap<>(8);

    private static final Map<String, Field> FIELD_CACHE = new ConcurrentHashMap<>(64);

    private static final String APPLICATION_BASE_PACKAGE = "BASE_PACKAGE";

    public static void setApplicationBasePackage(String basePackageName) {
        CONFIG.put(APPLICATION_BASE_PACKAGE, basePackageName);
    }

    public static String getApplicationBasePackage() {
        return CONFIG.get(APPLICATION_BASE_PACKAGE);
    }

    public static void registerSwitchFieldCache(String basePath, Field field) {
        FIELD_CACHE.put(basePath.concat("/").concat(field.getName()), field);
    }

    public static Map<String, Field> getSwitchFieldCache() {
        return FIELD_CACHE;
    }

    public static Field getTargetSwitchFieldCache(String path) {
        path = path.replaceFirst("/switch", "");
        return FIELD_CACHE.get(path);
    }
}
