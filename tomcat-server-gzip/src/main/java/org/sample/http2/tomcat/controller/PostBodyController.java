package org.sample.http2.tomcat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * springboot-tomcat
 *
 * Date: 2021/4/1
 */
@RestController
public class PostBodyController {
    @GetMapping("/tomcat/get")
    public String post() {
        return "ok";
    }
}
