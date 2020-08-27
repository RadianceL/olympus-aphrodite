package com.el.switcher.controller;

import com.el.switcher.data.SwitchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * switch客户端注册接口 <br/>
 * since 2020/8/26
 *
 * @author eddie.lys
 */
@Slf4j
@RestController
public class SwitchClientRegisterController {

    @PostMapping("/switch/data/register/{scope}")
    public SwitchResult<String> clientInit(@PathVariable String scope, @RequestBody String data) {
        log.info(data);
        return SwitchResult.ofSuccess(null);
    }
}
