package com.computerManagementSystem.controller;

import com.computerManagementSystem.pojo.Member;
import com.computerManagementSystem.pojo.MemberLogin;
import com.computerManagementSystem.service.MemberServiceImpl;
import com.computerManagementSystem.util.CookieUtil;
import com.computerManagementSystem.util.JWTUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

/**
 * @author 赖志宇
 * @date 2020/6/18 2:12
 */
@RequestMapping(value = "member",method = RequestMethod.POST)
@RestController
public class MemberInfoController {

    @Resource(name = "MemberService")
    private MemberServiceImpl memberService;

    /**
     * 登录验证
     * @param memberLogin
     * @param response
     * @return 账号密码正确返回true并把登录凭证写入cookie 否则 false
     */
    @PostMapping("login")
    public String loginVerify(@RequestBody MemberLogin memberLogin, HttpServletResponse response){
        if(memberService.verifyMember(memberLogin)){
            String id = memberLogin.getId();
            String token = JWTUtil.getToken("account",id);
            CookieUtil.setMemberCookie(response,token);
            return "true";
        }
        return "false";
    }

    /**
     * 获取个人信息页
     * @param request
     * @return member对象
     */
    @PostMapping("getMyInfo")
    public Member getMyInfo(HttpServletRequest request){
        String token = CookieUtil.getCookieValueNamedToken(request);
        String id = JWTUtil.getClaimNamedAccount(token);
        return memberService.getMyInfo(id);
    }

    /**
     * 更新个人信息
     * @param member
     * @param request
     * @return
     */
    @PostMapping("updateMyInfo")
    public String updateMyInfo(@RequestBody Member member,HttpServletRequest request){
        String token = CookieUtil.getCookieValueNamedToken(request);
        String id = JWTUtil.getClaimNamedAccount(token);
        member.setId(id);
        memberService.updatePersonalInfo(member);
        return "true";
    }

    /**
     * 修改密码，修改前会做密码认证
     * @param response
     * @param request
     * @param password
     * @param newPassword
     * @return 如果旧密码正确则成功修改密码,删除登录凭证返回true 否则返回false
     */
    @PostMapping("changePassword")
    public String changePassword(HttpServletResponse response,HttpServletRequest request, @RequestParam(value = "password") @NotBlank String password,@RequestParam(value = "newPassword") @NotBlank String newPassword){
        String token = CookieUtil.getCookieValueNamedToken(request);
        String id = JWTUtil.getClaimNamedAccount(token);
        if(memberService.changePassword(id,password,newPassword)){
            CookieUtil.removeMemberCookie(response,"auth_token");
            return "true";
        }
        return "false";
    }
}
