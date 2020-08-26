package com.el.switcher.controller;

import com.el.switcher.data.SwitchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * switch客户端注册接口 <br/>
 * since 2020/8/26
 *
 * @author eddie.lys
 */
@Slf4j
@RestController
public class SwitchClientRegisterController {

    @PostMapping("/switch/data/register/{appName}")
    public SwitchResult<String> clientInit(@PathVariable String appName, @RequestBody String data) {
        log.info(data);
        return SwitchResult.ofSuccess("");
    }
}
