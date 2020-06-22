package com.computerManagementSystem.controller;

import com.computerManagementSystem.pojo.Member;
import com.computerManagementSystem.pojo.MemberLogin;
import com.computerManagementSystem.service.MemberServiceImpl;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赖志宇
 * @date 2020/6/19 17:52
 */

@RequestMapping(value = "member",method = RequestMethod.POST)
@RestController
@Validated
public class MemberInfoAdminController {
    @Resource(name = "MemberService")
    private MemberServiceImpl memberService;
    @PostMapping(value = {"getMemberList", "getOperatorList", "getAdminList"})
    public Map<String, Object> sendMemberDataList(HttpServletRequest httpServletRequest, @RequestParam(value = "page") int page, @RequestParam(value = "limit") int limit) throws IOException {
        String url = httpServletRequest.getServletPath();
        Map<String, String> urlMap = new HashMap<String, String>() {
            {
                put("/member/getMemberList", "'普通队员' or role ='管理员' or role ='接线员'");
                put("/member/getOperatorList", "'接线员'");
                put("/member/getAdminList", "'管理员'");
            }

        };
        List<Member> list = memberService.getMemberListByRole(page,limit,urlMap.get(url));
        Map<String,Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("count",memberService.getCountByRole(urlMap.get(url)));
        res.put("data",list);
        return res;
    }

    @PostMapping(value = {"addOperator", "addAdmin", "removeRole"})
    public String changeRole(HttpServletRequest httpServletRequest,@RequestBody MemberLogin memberLogin) throws IOException {
        String url = httpServletRequest.getServletPath();
        Map<String, String> urlMap = new HashMap<String, String>() {
            {
                put("/member/removeRole", "普通队员");
                put("/member/addOperator", "接线员");
                put("/member/addAdmin", "管理员");
            }

        };
        memberLogin.setRole(urlMap.get(url));
        if(memberService.changeRole(memberLogin))
            return "true";
        return "false";
    }

    @PostMapping("restPassword")
    public String restPassword(@RequestBody MemberLogin memberLogin){
        memberService.resetPassword(memberLogin);
        return "true";
    }

    @PostMapping("addMember")
    public String addMember(@RequestBody Member member){
        if(memberService.isMemberExit(member.getId())){
            memberService.addMember(member);
            return "true";
        }
        return "false";
    }
}
