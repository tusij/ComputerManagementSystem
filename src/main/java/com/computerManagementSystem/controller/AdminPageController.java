package com.computerManagementSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 赖志宇
 * @date 2020/6/18 23:11
 */
@RequestMapping(value = "member")
@Controller
public class AdminPageController {

    /**
     * 添加管理员页
     * @return
     */
    @GetMapping(value = "addAdmin")
    public ModelAndView addAdminPage(){
        return new ModelAndView("Admin/AddAdmin");
    }

    /**
     * 添加成员页
     * @return
     */
    @GetMapping(value = "addMember")
    public ModelAndView addMemberPage(){
        return new ModelAndView("Admin/AddMember");
    }

    /**
     * 添加接线员页
     * @return
     */
    @GetMapping(value = "addOperator")
    public ModelAndView addOperatorPage(){
        return new ModelAndView("Admin/AddOperator");
    }

    /**
     * 管理员列表
     * @return
     */
    @GetMapping(value = "adminList")
    public ModelAndView adminListPage(){
        return new ModelAndView("Admin/AdminList");
    }

    /**
     * 成员列表
     * @return
     */
    @GetMapping(value = "memberList")
    public ModelAndView memberListPage(){
        return new ModelAndView("Admin/MemberList");
    }

    /**
     * 接线员列表
     * @return
     */
    @GetMapping(value = "operatorList")
    public ModelAndView operatorListPage(){
        return new ModelAndView("Admin/OperatorList");
    }

    /**
     * 所有已经结束的任务列表页
     * @return
     */
    @GetMapping(value = "memberTaskList")
    public ModelAndView Page(){
        return new ModelAndView("Admin/MemberTaskList");
    }
}
