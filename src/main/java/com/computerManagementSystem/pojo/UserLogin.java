package com.computerManagementSystem.pojo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author 赖志宇
 * @date 2020/6/17 1:34
 */
public class UserLogin {
    @NotBlank@Length(max = 11,min = 11)
    private String phoneNumber;
    @NotBlank@Length(min = 6,max = 6)
    private String code;

    public UserLogin(){

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
