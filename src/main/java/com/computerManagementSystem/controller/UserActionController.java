package com.computerManagementSystem.controller;

import com.computerManagementSystem.pojo.RepairForm;
import com.computerManagementSystem.pojo.UserLogin;
import com.computerManagementSystem.service.RepairServiceImpl;
import com.computerManagementSystem.util.CookieUtil;
import com.computerManagementSystem.util.JWTUtil;
import com.computerManagementSystem.util.MessageUtil;
import com.github.qcloudsms.httpclient.HTTPException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赖志宇
 * @date 2020/6/17 1:25
 */

@RequestMapping(value = "repair", method = RequestMethod.POST)
@Validated
@RestController
public class UserActionController {
    @Resource(name = "RepairService")
    private RepairServiceImpl repairService;

    /**
     * 接收前端传过来的电话号码，并发送验证码
     * @param phoneNumber
     * @param request
     * @throws HTTPException
     * @throws IOException
     */
    @PostMapping(value = "sendMessage")
    public void sendMessage(@RequestParam(value = "phoneNumber") @NotBlank(message = "电话号码不能为空") String phoneNumber, HttpServletRequest request) throws HTTPException, IOException {
        request.getSession().setAttribute("phoneNumber", phoneNumber);
        request.getSession().setAttribute("code", MessageUtil.sendMessage(phoneNumber));
    }

    /**
     * 将验证码，手机号和传过来的做比较
     * @param userLogin
     * @param request
     * @param response
     * @return 一致true 不一致 false
     */
    @PostMapping(value = "login")
    public String verifyUser(@RequestBody @Valid UserLogin userLogin, HttpServletRequest request, HttpServletResponse response) {
        if (userLogin.getPhoneNumber().equals(request.getSession().getAttribute("phoneNumber")) && userLogin.getCode().equals(request.getSession().getAttribute("code"))) {
            String token = JWTUtil.getToken("account", userLogin.getPhoneNumber());
            CookieUtil.setUserCookie(response, token);
            return "true";
        }
        return "false";
    }

    /**
     * 接收报修表，后期会加入验证防止重复提交
     * @param repairForm
     * @param request
     * @return
     */
    @PostMapping(value = "sendRepairForm")
    public String acceptRepair(@RequestBody @Valid RepairForm repairForm, HttpServletRequest request) {
        String token = CookieUtil.getCookieValueNamedToken(request);
        String phoneNumber = JWTUtil.getClaimNamedAccount(token);
        repairForm.setPhoneNumber(phoneNumber);
        repairForm.setState("未接取");
        repairService.acceptRepair(repairForm);
        return "true";
    }

    /**
     * 用户查看历史保修信息
     * @param request
     * @param page 页码
     * @param limit 每次回传数量限制
     * @return 回传一个map 其中 code是转态码 count是数据总量（和每次回传数量区别） data回传的数据
     */
    @PostMapping(value = "getHistoryRepairForms")
    public Map<String,Object> sendHistoryRepairForm(HttpServletRequest request, @RequestParam(value = "page") int page, @RequestParam(value = "limit") int limit){
        String token = CookieUtil.getCookieValueNamedToken(request);
        String phoneNumber = JWTUtil.getClaimNamedAccount(token);
        Map<String,Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("count", repairService.queryUserRepairNumsByPhoneNumber(phoneNumber));
        res.put("data", repairService.userQuerySelfRepair(page,limit,phoneNumber));
        return res;
    }

}
