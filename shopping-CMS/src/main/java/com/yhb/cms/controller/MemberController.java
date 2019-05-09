package com.yhb.cms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhb.cms.exception.BizException;
import com.yhb.cms.exception.BizExceptionEnum;
import com.yhb.common.result.BaseResult;
import com.yhb.member.entity.MemberInfo;
import com.yhb.member.service.MemberInfoService;
import com.yhb.member.vo.SimpleMemberInfo;
import com.yhb.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/7 19:28
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping("member")
public class MemberController {
    @Reference(version = "1.0.0")
    private MemberInfoService memberInfoService;
    @Reference(version = "1.0.0")
    private RedisService redisService;

    @GetMapping("/members")
    public BaseResult getMembers(){
        List<MemberInfo> allMemberInfos = memberInfoService.getAllMemberInfos();
        BaseResult baseResult = BaseResult.setSuccessResult(allMemberInfos);
        return baseResult;
    }

    @GetMapping("/member/{id}")
    public BaseResult getMember(@PathVariable(value = "id")Long id){
        SimpleMemberInfo simpleMemberInfo = memberInfoService.getMemberInfoById(id);
        return BaseResult.setSuccessResult(simpleMemberInfo);
    }

    @PostMapping("/member")
    public BaseResult insertMember(@RequestBody MemberInfo memberInfo){
        Integer integer = memberInfoService.insertMember(memberInfo);
        if (integer > 0){
            return BaseResult.setSuccessMessage("添加成功");
        }else {
            return BaseResult.setErrorResult("添加失败");
        }
    }

    @PutMapping("/member")
    public BaseResult updateMember(@RequestBody MemberInfo memberInfo){
        if (memberInfo.getId() == null){
            return BaseResult.setErrorResult("id不能为空");
        }
        Integer integer = memberInfoService.updateMemberInfoById(memberInfo);
        if (integer > 0){
            return BaseResult.setSuccessMessage("修改成功");
        }else {
            return BaseResult.setErrorResult("修改失败");
        }
    }

    @DeleteMapping("/member/{id}")
    public BaseResult deleteMember(@PathVariable(value = "id")Long id){
        if (id == null){
            return BaseResult.setErrorResult("id不能为空");
        }
        Integer integer = memberInfoService.deleteMemberInfoById(id);
        if (integer > 0){
            return BaseResult.setSuccessMessage("删除成功");
        }else {
            return BaseResult.setErrorResult("删除失败");
        }
    }
}
