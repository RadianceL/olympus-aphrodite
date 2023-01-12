package com.olympus.aphrodite.service.impl;

import com.olympus.aphrodite.data.SwitchFieldInfo;
import com.olympus.aphrodite.data.TargetPath;
import com.olympus.aphrodite.data.enums.ClassTypeMapEnum;
import com.olympus.aphrodite.repository.SwitchDataRepository;
import com.olympus.aphrodite.service.DataSourceCoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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
    /**
     * 数据层
     */
    private final SwitchDataRepository switchDataRepository;

    @Override
    public boolean updateTargetField(TargetPath targetPath, String classType, String targetValue) {
        try {
            ClassTypeMapEnum classMap = ClassTypeMapEnum.findClassMap(classType);
            Object targetObject = classMap.convertTo(targetValue);
            // 校验更新字段类型
            log.info("更新目标字段：{}, value: [{}]", targetPath.getPath(), targetObject);
            SwitchFieldInfo switchFieldInfo = verifyDataClassType(targetPath, targetObject);

            // 实际更新字段 并创建数据节点/switch/update/${application}/updateVersion
            switchDataRepository.save(switchFieldInfo);

            return true;
        }catch (Throwable e) {
            log.error("更新目标字段异常", e);
            return false;
        }
    }

    public SwitchFieldInfo verifyDataClassType(TargetPath targetPath, Object targetValue) {
        // 查询字段信息
        Optional<SwitchFieldInfo> sourceFieldDataOptional =  switchDataRepository.findOne(Example.of(new SwitchFieldInfo(){{
            setFieldPath(targetPath.getPath());
        }}));
        SwitchFieldInfo sourceFieldData = sourceFieldDataOptional.orElse(null);
        if (Objects.isNull(sourceFieldData)) {
            return null;
        }
        Class<?> targetClassType = targetValue.getClass();
        Class<?> sourceClassType = sourceFieldData.getClassType();
        if (targetClassType.isAssignableFrom(sourceClassType) && !targetClassType.getName().equals(sourceClassType.getName())) {
            throw new RuntimeException("更新数据类型与目标类型不一致，source: [ " + sourceClassType.getName() + " ]，target: [ " + targetClassType.getName() + " ]");
        }
        sourceFieldData.setValue(targetValue);
        return sourceFieldData;
    }

}
