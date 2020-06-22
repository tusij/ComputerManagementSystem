package com.computerManagementSystem.service;

import com.computerManagementSystem.pojo.Member;
import com.computerManagementSystem.pojo.MemberLogin;

import java.util.List;

/**
 * @author 赖志宇
 * @date 2020/6/19 1:42
 */
public interface AdminOperateMemberService {
    /**
     * 根据id查询成员是否存在
     * @param id
     * @return 0不存在，1存在
     */
    Boolean isMemberExit(String id);

    /**
     * 加入一个新成员，无需关注MemberLogin的生成，数据库会通过tigger实现
     * @param member
     */
    void addMember(Member member);


    /**
     * 改变成员的权限，这个方法用于权限升级，权限只能从低到高升级,或者直接降为普通成员
     * @Param memberLogin
     * @return true修改成功,false修改失败 原因：不是权限移除或者本次权限调整小于等于目前权限
     */
    Boolean changeRole(MemberLogin memberLogin);

    /**
     * 通过权限来查询成员
     * @param role
     * @return 成员列表
     */
    List<Member> getMemberListByRole(int page, int limit,String role);

    int getCountByRole(String role);
}
