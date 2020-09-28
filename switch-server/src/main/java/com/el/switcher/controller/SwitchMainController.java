package com.el.switcher.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 核心接口 <br/>
 * since 2020/9/11
 *
 * @author eddie.lys
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SwitchMainController {

    @GetMapping("/switch/data/application/list")
    public List<String> applicationList() {
        return new ArrayList<>();
    }

    /**
     * 获取应用下所有字段信息
     * @param application       应用名称
     * @return                  应用下所有字段
     */
    @GetMapping("/switch/data/{application}/info")
    public List<?> showCurrentSwitchData(@PathVariable String application) {
        return null;
    }

    @PostMapping("/switch/data/{application}/{field}")
    public Void updateField(@PathVariable String application, @PathVariable String field, Object targetFieldValue){
        return null;
    }

}
