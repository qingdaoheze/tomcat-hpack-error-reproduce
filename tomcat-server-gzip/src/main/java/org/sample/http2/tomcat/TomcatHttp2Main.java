package org.sample.http2.tomcat;

import org.sample.http2.tomcat.exception.GlobalUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot-tomcat
 *
 * Date: 2021/4/1
 */
@SpringBootApplication
public class TomcatHttp2Main {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalUncaughtExceptionHandler());
        SpringApplication.run(TomcatHttp2Main.class, args);
    }
}