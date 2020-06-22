package com.computerManagementSystem.pojo;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author 赖志宇
 * @date 2020/6/18 2:17
 */
@Component("MemberLogin")
public class MemberLogin implements Serializable {
    private String id;
    private String passwordMD5;
    private String role;

    public MemberLogin() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswordMD5() {
        return passwordMD5;
    }

    public void setPasswordMD5(String passwordMD5) {
        this.passwordMD5 = passwordMD5;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void changePasswordToMD5(){
        this.passwordMD5 = DigestUtils.md5Hex(this.passwordMD5);
    }
    @Override
    public String toString() {
        return "MemberLogin{" +
                "id='" + id + '\'' +
                ", passwordMD5='" + passwordMD5 + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}