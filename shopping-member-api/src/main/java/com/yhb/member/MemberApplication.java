package com.yhb.member;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.yhb.member.entity.MemberInfo;
import com.yhb.member.service.MemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@EnableDubbo
public class MemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }

}
