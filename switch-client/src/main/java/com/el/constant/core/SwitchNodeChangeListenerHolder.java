package com.el.constant.core;

import com.el.constant.data.SwitchFieldInfo;
import com.el.constant.utils.ConstantValueUpdate;
import com.el.zk.data.EventData;
import com.el.zk.serialize.SerializingUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * <br/>
 * since 2020/9/9
 *
 * @author eddie.lys
 */
@Slf4j
public class SwitchNodeChangeListenerHolder {

    @SneakyThrows
    public static void update(EventData eventData) {
        Field targetSwitchFieldCache = SwitchApplicationSystem.getTargetSwitchFieldCache(eventData.getPath());
        if (Objects.isNull(targetSwitchFieldCache)) {
            return;
        }
        byte[] data = eventData.getData();
        SwitchFieldInfo switchFieldInfo = SerializingUtil.deserialize(data, SwitchFieldInfo.class);
        boolean updateTargetBoolean = ConstantValueUpdate.updateTargetBoolean(targetSwitchFieldCache, switchFieldInfo.getValue());
        if (updateTargetBoolean) {
            log.info("switch - update event source field: {}, source value: [{}], target value : [{}]",
                    targetSwitchFieldCache.getName(),
                    FieldUtils.readStaticField(targetSwitchFieldCache, true),
                    switchFieldInfo.getValue()
            );
        }else {
            log.error("switch - update event source field error: {}, source value: [{}], target value : [{}]",
                    targetSwitchFieldCache.getName(),
                    FieldUtils.readStaticField(targetSwitchFieldCache, true),
                    switchFieldInfo.getValue()
            );
        }
    }
}
