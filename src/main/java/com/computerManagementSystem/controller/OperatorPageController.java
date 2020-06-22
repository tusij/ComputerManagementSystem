package com.computerManagementSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 赖志宇
 * @date 2020/6/18 23:00
 */
@RequestMapping(value = "member")
@Controller
public class OperatorPageController {
    /**
     * 未完成任务的页面
     * @return
     */
    @GetMapping(value = "doingTaskList")
    public ModelAndView DoingListPage() {
        return new ModelAndView("Operator/DoingTaskList");
    }

    /**
     * 线下任务的页面
     * @return
     */
    @GetMapping(value = "offlineTaskList")
    public ModelAndView OfflineListPage() {
        return new ModelAndView("Operator/OfflineTaskList");
    }

    /**
     * 未接取的任务的页面
     * @return
     */
    @GetMapping(value = "waitingTaskList")
    public ModelAndView WaitingListPage() {
        return new ModelAndView("Operator/WaitingTaskList");
    }
}
