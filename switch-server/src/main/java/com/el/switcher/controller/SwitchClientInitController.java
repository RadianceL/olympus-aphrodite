package com.el.switcher.controller;

import com.el.switcher.data.SwitchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * switch客户端初始化接口 <br/>
 * since 2020/8/26
 *
 * @author eddie.lys
 */
@Slf4j
@RestController
public class SwitchClientInitController {

    @GetMapping("/switch/data/find-all/{scope}")
    public SwitchResult<Map<String, Object>> clientInit(@PathVariable String scope) {
        log.info(scope);
        Map<String, Object> switchResultMap = new HashMap<>(8);
        switchResultMap.put("TEST", false);
        return SwitchResult.ofSuccess(switchResultMap);
    }
}
