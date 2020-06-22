package com.computerManagementSystem.pojo;

/**
 * @author 赖志宇
 * @date 2020/6/17 22:09
 */
public class Repair {

    private int rid;

    private String id;

    public Repair() {

    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "rid=" + rid +
                ", id='" + id + '\'' +
                '}';
    }
}
