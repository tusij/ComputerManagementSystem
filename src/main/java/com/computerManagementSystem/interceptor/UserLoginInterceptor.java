package com.computerManagementSystem.interceptor;

import com.computerManagementSystem.util.CookieUtil;
import com.computerManagementSystem.util.JWTUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 赖志宇
 * @date 2020/6/21 2:40
 */
public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtil.getCookieValueNamedToken(request);
        if(request.getServletPath().equals("/repair/login")){
            if (request.getMethod().equals("POST"))
                return true;
            else if(JWTUtil.verifyToken(token)){
                response.sendRedirect("index");
                return true;
            }
            else
                return true;
        }
        else if(!JWTUtil.verifyToken(token)){
            response.sendRedirect("login");
            return false;
        }
        else
            return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
