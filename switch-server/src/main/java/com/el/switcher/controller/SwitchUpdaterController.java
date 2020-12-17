package com.el.switcher.controller;

import com.el.switcher.entity.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 更新回调控制器 </br>
 * since 2020/12/6
 *
 * @author eddie
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SwitchUpdaterController {

    @PostMapping("/switch/data/application/update/callback")
    public Response<String> callback() {
        return Response.ofSuccess(null);
    }
}
