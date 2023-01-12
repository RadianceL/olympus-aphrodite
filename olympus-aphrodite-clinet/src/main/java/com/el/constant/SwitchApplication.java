package com.el.constant;

import com.el.constant.annotation.EnableSwitchAutoConfiguration;
import com.el.constant.constant.TestConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSwitchAutoConfiguration
public class SwitchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwitchApplication.class, args);
        System.out.println(TestConstant.TEST);
    }

}
