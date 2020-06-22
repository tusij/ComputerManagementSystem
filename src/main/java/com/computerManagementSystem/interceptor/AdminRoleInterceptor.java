package com.computerManagementSystem.interceptor;

import com.computerManagementSystem.service.MemberServiceImpl;
import com.computerManagementSystem.util.CookieUtil;
import com.computerManagementSystem.util.JWTUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author 赖志宇
 * @date 2020/6/21 22:05
 */
public class AdminRoleInterceptor implements HandlerInterceptor {
    @Resource(name = "MemberService")
    private MemberServiceImpl memberInfoService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String loginToken = CookieUtil.getCookieValueNamedToken(request);

        String id = JWTUtil.getClaimNamedAccount(loginToken);

        String roleToken = CookieUtil.getCookieValueNamedRoleToken(request);
        HashMap<String, Integer> roleMap = new HashMap<String, Integer>() {
            {
                put("普通队员", 0);
                put("接线员", 1);
                put("管理员", 2);
                put("超管", 3);
            }
        };
        if(JWTUtil.verifyToken(roleToken)) {
            String role = JWTUtil.getClaimNamedRole(roleToken);
            if (roleMap.get(role) < 2)
                return false;
            else
                return true;
        }
        else{
            String role = memberInfoService.getMemberRoleByID(id);
            roleToken = JWTUtil.getToken("role",role);
            CookieUtil.setRoleCookie(response,roleToken);
            if (roleMap.get(role) < 2)
                return false;
            else
                return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
