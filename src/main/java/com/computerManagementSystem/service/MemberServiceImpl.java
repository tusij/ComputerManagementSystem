package com.computerManagementSystem.service;

import com.computerManagementSystem.mapper.MemberInfoMapper;
import com.computerManagementSystem.mapper.MemberLoginMapper;
import com.computerManagementSystem.pojo.Member;
import com.computerManagementSystem.pojo.MemberLogin;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赖志宇
 * @date 2020/6/19 1:25
 *
 * 内聚不够 验证用户和修改用户需要获取到memberLogin对象比较，应该写一个memberLogin获取方法，虽然可以通过mapper获取，但这会使得某些方法实现了两个功能
 * 这里暂时不做优化作为未处理优化给运维负责
 */
@Service("MemberService")
public class MemberServiceImpl implements MemberInfoService, AdminOperateMemberService {

    @Resource
    private MemberInfoMapper memberInfoMapper;

    @Resource
    private MemberLoginMapper memberLoginMapper;

    @Override
    public Boolean verifyMember(MemberLogin login) {
        login.changePasswordToMD5();
        String passwordMD5 = login.getPasswordMD5();

        if (passwordMD5.equals(memberLoginMapper.queryMemberLoginById(login.getId()).getPasswordMD5()))
            return true;
        return false;
    }

    @Override
    public Member getMyInfo(String id) {
        return memberInfoMapper.queryMemberByID(id);
    }

    @Override
    public Boolean isMemberExit(String id) {
        if (memberLoginMapper.queryMemberIsExit(id) == 0)
            return true;
        return false;
    }

    @Override
    public void addMember(Member member) {
        memberInfoMapper.insertMember(member);
    }


    @Override
    public Boolean changeRole(MemberLogin memberLogin) {
        HashMap<String, Integer> map = new HashMap<String, Integer>() {
            {
                put("普通队员", 0);
                put("接线员", 1);
                put("管理员", 2);
                put("超管", 3);
            }
        };
        String id = memberLogin.getId();
        String role = memberLogin.getRole();
        String oldRole = getMemberRoleByID(id);
        if (!role.equals("普通队员") && map.get(role) <= map.get(oldRole))
            return false;
        memberLoginMapper.updateMemberLogin(memberLogin);
        return true;
    }

    @Override
    public List<Member> getMemberListByRole(int page, int limit, String role) {
        Map<String, Object> attribute = new HashMap<>();
        attribute.put("start", (page - 1) * limit);
        attribute.put("limit", limit);
        attribute.put("role", role);
        return memberInfoMapper.queryMemberByRole(attribute);
    }

    @Override
    public int getCountByRole(String role) {
        return memberLoginMapper.getCountByRole(role);
    }

    @Override
    public Boolean changePassword(String id,String password,String newPassword) {
        MemberLogin memberLogin = memberLoginMapper.queryMemberLoginById(id);
        if(DigestUtils.md5Hex(password).equals(memberLogin.getPasswordMD5())){
            memberLogin.setPasswordMD5(DigestUtils.md5Hex(newPassword));
            memberLoginMapper.updateMemberLogin(memberLogin);
            return true;
        }
        else
            return false;

    }

    @Override
    public void updatePersonalInfo(Member member) {
        memberInfoMapper.update(member);
    }

    @Override
    public String getMemberRoleByID(String id) {
        return memberLoginMapper.queryRoleById(id);
    }

    public void resetPassword(MemberLogin memberLogin){
        memberLogin.setPasswordMD5(DigestUtils.md5Hex("000000"));
        memberLoginMapper.updateMemberLogin(memberLogin);
    }

}
