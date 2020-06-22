package com.computerManagementSystem.service;

import com.computerManagementSystem.pojo.Member;
import com.computerManagementSystem.pojo.MemberLogin;

/**
 * @author 赖志宇
 * @date 2020/6/18 2:16
 */
public interface MemberInfoService {
    /**
     * 验证账号密码是否正确，多用于登录
     * @param login
     * @return 密码正确返回true,错误false
     */
    Boolean verifyMember(MemberLogin login);

    /**
     * 根据成员id获取成员信息
     * @param id
     * @return 返回member对象
     */
    Member getMyInfo(String id);



    /**
     * 修改密码
     */
    Boolean changePassword(String id,String password,String newPasswprd);


    /**
     * 更新个人信息
     * @Param member
     */
    void updatePersonalInfo(Member member);

    String getMemberRoleByID(String id);
}
