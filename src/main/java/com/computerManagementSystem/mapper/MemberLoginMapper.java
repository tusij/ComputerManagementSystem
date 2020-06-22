package com.computerManagementSystem.mapper;

import com.computerManagementSystem.pojo.MemberLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 赖志宇
 * @date 2020/6/18 22:01
 */
@Mapper
public interface MemberLoginMapper {
    MemberLogin queryMemberLoginById(String id);

    void updateMemberLogin(MemberLogin memberLogin);

    int queryMemberIsExit(String id);

    String queryRoleById(String id);


    int getCountByRole(@Param("role") String role);
}
