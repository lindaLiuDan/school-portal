package com.info;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 功能描述: (⊙o⊙)本地生活的API主启动方法
 *
 * @Params: * @param null
 * @Author: Gaosx  By User
 * @Date: 2019/5/30 16:42
 * @Return:
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.info.modules.*.dao"})
public class OwnerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(OwnerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OwnerApplication.class);
    }
}
