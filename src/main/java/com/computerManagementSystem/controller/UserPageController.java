package com.computerManagementSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 赖志宇
 * @date 2020/6/17 0:40
 * 用于报修用户的页面相应
 */
@Controller
@RequestMapping(value = "repair")
public class UserPageController {

    /**
     * 用于报修用户登录页面的加载
     * @return
     */
    @GetMapping(value = "login")
    public ModelAndView userLoginPage(){
        return new ModelAndView("User/UserLogin");
    }

    /**
     * 用户主页
     * @return
     */
    @GetMapping(value = "index")
    public ModelAndView userIndexPage(){
        return new ModelAndView("User/UserIndex");
    }

    /**
     * 用户报修页面
     * @return
     */
    @GetMapping(value = "repairForm")
    public ModelAndView repairFormPage(){
        return new ModelAndView("User/repairForm");
    }

    /**
     * 用户报修历史页
     * @return
     */
    @GetMapping(value = "history")
    public ModelAndView RepairedFormPage(){
        return new ModelAndView("User/historyRepairForms");
    }
}
