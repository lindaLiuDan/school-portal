package com.info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 功能描述:
 *
 * @Params: * @param null
 * @Author: Gaosx  By User
 * @Date: 2019/6/8 11:57
 * @Return:
 */
@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class GeneratorApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GeneratorApplication.class);
    }
}
