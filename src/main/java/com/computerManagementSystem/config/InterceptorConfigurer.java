package com.computerManagementSystem.config;

import com.computerManagementSystem.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 赖志宇
 * @date 2020/6/21 14:06
 */
@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {
    @Bean
    public AdminRoleInterceptor adminRoleInterceptor() {
        return new AdminRoleInterceptor();
    }

    @Bean
    public SuperAdminRoleInterceptor superAdminRoleInterceptor() {
        return new SuperAdminRoleInterceptor();
    }

    @Bean
    public OperatorRoleInterceptor operatorRoleInterceptor() {
        return new OperatorRoleInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MemberLoginInterceptor()).addPathPatterns("/member/**");
        registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/repair/**")
                .excludePathPatterns("/repair/sendMessage");
        registry.addInterceptor(operatorRoleInterceptor())
                .addPathPatterns("/member/offlineTaskList", "/member/waitingTaskList", "/member/doingTaskList")
                .addPathPatterns("/member/getWaitingTaskList", "/member/getDoingTaskList", "/member/getOfflineTaskList")
                .addPathPatterns("/member/releaseTask", "/member/finishTask", "/member/cancelTask", "/member/finishOfflineTask");
        registry.addInterceptor(adminRoleInterceptor())
                .addPathPatterns("/member/memberTaskList", "/member/getMemberTaskList")
                .addPathPatterns("/member/memberList", "/member/operatorList")
                .addPathPatterns("/member/addMember", "/member/addOperator", "/member/removeRole", "/member/restPassword")
                .addPathPatterns("/member/getMemberList", "/member/getOperatorList");
        registry.addInterceptor(superAdminRoleInterceptor())
                .addPathPatterns("/member/addAdmin","/member/getAdminList");

    }
}




