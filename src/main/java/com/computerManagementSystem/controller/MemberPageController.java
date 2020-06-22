package com.computerManagementSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 赖志宇
 * @date 2020/6/18 1:43
 */

@RequestMapping(value = "member",method = RequestMethod.GET)
@Controller
public class MemberPageController {
    /**
     * 用户主页
     * @return
     */
    @GetMapping("index")
    public ModelAndView IndexPage(){
        return new ModelAndView("Member/MemberIndex");
    }

    /**
     * 用户登录页
     * @return
     */
    @GetMapping("login")
    public ModelAndView loginPage(){
        return new ModelAndView("Member/MemberLogin");
    }

    /**
     * 用户个人信息页
     * @return
     */
    @GetMapping("personalInfoPage")
    public ModelAndView personalInformationPage(){
        return new ModelAndView("Member/PersonalMessage");
    }

    /**
     * 用户查看个人任务页
     * @return
     */
    @GetMapping("myTaskList")
    public ModelAndView memberTaskListPage(){
        return new ModelAndView("Member/MyTaskList");
    }

    /**
     * 用户修改密码页
     * @return
     */
    @GetMapping("changePassword")
    public ModelAndView changePasswordPage(){
        return new ModelAndView("Member/ChangePassword");
    }
}
