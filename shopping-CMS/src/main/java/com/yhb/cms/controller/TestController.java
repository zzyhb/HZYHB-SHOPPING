package com.yhb.cms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhb.cms.exception.BizException;
import com.yhb.common.result.BaseResult;
import com.yhb.common.util.DateUtil;
import com.yhb.common.util.JSONUtil;
import com.yhb.member.service.MemberInfoService;
import com.yhb.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/7 19:28
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping("test")
public class TestController {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    // 为了安全起见  暂时将RAM账号密码删除
    
    private final String bucketName = "hzyhb";
    private final String key = "mOGcJ5lTaxlyufgU3zxJcZi2madlHP";

    @Reference(version = "1.0.0")
    private MemberInfoService memberInfoService;
    @Reference(version = "1.0.0")
    private RedisService redisService;

    @RequestMapping("/test.do")
    public BaseResult test(MultipartFile file) throws BizException {
        log.info(JSONUtil.toJsonString(file.getName().toString().split(".")));
        log.info(JSONUtil.toJsonString(file.getOriginalFilename().toString().split(".")));
        log.info(DateUtil.convertDateToString(DateUtil.String_YMDHMS_FORMAT,new Date())+file.getOriginalFilename().split(".")[1]);
        /*// 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        String content = "Thank you for using Aliyun Object Storage Service";
        // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
        try {
            ossClient.putObject(bucketName, LocalDateTime.now().toString(), new ByteArrayInputStream(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 关闭OSSClient。
        ossClient.shutdown();*/
        return new BaseResult();
    }
}
