package com.el.switcher.entity.enumerate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.olympus.base.utils.support.exception.ExtendRuntimeException;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * 类映射枚举 </br>
 * since 2020/11/30
 *
 * @author eddie
 */
public enum ClassTypeMapEnum {

    /**
     * 映射
     */
    BOOLEAN("Boolean", "", Boolean.class) {
        @Override
        public Object convertTo(String targetValue) {
            if(StringUtils.isBlank(targetValue)) {
                return null;
            }
            return Boolean.valueOf(targetValue);
        }
    },
    BYTE("Byte", "", Byte.class) {
        @Override
        public Object convertTo(String targetValue) {
            if(StringUtils.isBlank(targetValue)) {
                return null;
            }
            return null;
        }
    },
    DOUBLE("Double", "", Double.class) {
        @Override
        public Object convertTo(String targetValue) {
            if(StringUtils.isBlank(targetValue)) {
                return null;
            }
            return Double.valueOf(targetValue);
        }
    },
    FLOAT("Float", "", Float.class) {
        @Override
        public Object convertTo(String targetValue) {
            if(StringUtils.isBlank(targetValue)) {
                return null;
            }
            return Float.valueOf(targetValue);
        }
    },
    CHARACTER("Character", "", Character.class) {
        @Override
        public Object convertTo(String targetValue) {
            if (StringUtils.isBlank(targetValue)) {
                return null;
            }
            if (StringUtils.length(targetValue) > 1) {
                throw new ExtendRuntimeException("target value too long for Character");
            }
            return null;
        }
    },
    SHORT("Short", "", Short.class) {
        @Override
        public Object convertTo(String targetValue) {
            if(StringUtils.isBlank(targetValue)) {
                return null;
            }
            return Short.valueOf(targetValue);
        }
    },
    INTEGER("Integer", "", Integer.class) {
        @Override
        public Object convertTo(String targetValue) {
            if(StringUtils.isBlank(targetValue)) {
                return null;
            }
            return Integer.valueOf(targetValue);
        }
    },
    STRING("String", "", String.class) {
        @Override
        public Object convertTo(String targetValue) {
            if(StringUtils.isBlank(targetValue)) {
                return null;
            }
            return targetValue;
        }
    },
    LONG("Long", "", Long.class) {
        @Override
        public Object convertTo(String targetValue) {
            if(StringUtils.isBlank(targetValue)) {
                return null;
            }
            return Long.valueOf(targetValue);
        }
    },
    MAP("Map", "", Map.class) {
        @Override
        public Object convertTo(String targetValue) {
            if(StringUtils.isBlank(targetValue)) {
                return null;
            }
            return JSON.parseObject(targetValue);
        }
    },
    SET("Set", "", Set.class) {
        @Override
        public Object convertTo(String targetValue) {
            if(StringUtils.isBlank(targetValue)) {
                return null;
            }
            JSONArray array = JSON.parseArray(targetValue);
            Object[] targetArrays = array.toArray();
            return new HashSet<>(Arrays.asList(targetArrays));
        }
    },
    LIST("List", "", List.class) {
        @Override
        public Object convertTo(String targetValue) {
            if(StringUtils.isBlank(targetValue)) {
                return null;
            }
            JSONArray targetArrays = JSON.parseArray(targetValue);
            return Arrays.asList(targetArrays.toArray());
        }
    };

    ClassTypeMapEnum(String mapCode, String desc, Class<?> classType) {
        this.mapCode = mapCode;
        this.desc = desc;
        this.classType = classType;
    }

    private final String mapCode;

    private final String desc;

    private final Class<?> classType;

    public String getMapCode() {
        return mapCode;
    }

    public String getDesc() {
        return desc;
    }

    public Class<?> getClassType() {
        return classType;
    }

    public static ClassTypeMapEnum findClassMap(String mapCode) {
        if (StringUtils.isBlank(mapCode)) {
            throw new ExtendRuntimeException("map-code can't be blanl");
        }

        for (ClassTypeMapEnum classTypeMapEnum : values()) {
            if (classTypeMapEnum.getMapCode().equals(mapCode)) {
                return classTypeMapEnum;
            }
        }
        throw new ExtendRuntimeException("un-support class map");
    }

    /**
     * 映射到类
     * @param targetValue       目标值
     * @return                  目标对象
     */
    public abstract Object convertTo(String targetValue);
}
