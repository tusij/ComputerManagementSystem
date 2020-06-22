package com.computerManagementSystem.mapper;

import com.computerManagementSystem.pojo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author 赖志宇
 * @date 2020/6/18 22:01
 */
@Mapper
public interface MemberInfoMapper {
    void insertMember(Member member);

    Member queryMemberByID(String id);

    List<Member> queryAllMember(Map<String, Object> attribute);

    List<Member> queryMemberByRole(Map<String, Object> attribute);

    String queryNameByID(String id);


    void update(Member member);

    List<Member> queryByName(String name);
}
