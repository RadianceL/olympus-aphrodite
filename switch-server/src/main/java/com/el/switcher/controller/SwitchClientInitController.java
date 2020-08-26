package com.el.switcher.controller;

import com.el.switcher.data.SwitchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * switch客户端初始化接口 <br/>
 * since 2020/8/26
 *
 * @author eddie.lys
 */
@Slf4j
@RestController
public class SwitchClientInitController {

    @GetMapping("/switch/data/find-all/{appName}")
    public SwitchResult<String> clientInit(@PathVariable String appName) {
        log.info(appName);
        return SwitchResult.ofSuccess("{\"TEST\" : false}");
    }
}
