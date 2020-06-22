package com.computerManagementSystem.service;

import com.computerManagementSystem.pojo.RepairForm;

import java.util.List;
import java.util.Map;

/**用于维修相关的业务
 * 本接口的方法更加关注做了什么
 * @author 赖志宇
 * @date 2020/6/17 2:22
 */
public interface RepairService {
    /**
     * 用于接受报修表
     * @param repairForm
     */
    void acceptRepair(RepairForm repairForm);

    /**
     * 修改报修状态 未接取->未完成、取消 未完成->完成、取消
     * 参数有问题
     */
    void modifyRepairStatus(int rid,String state);

    /**
     *
     * @param idList
     * @param rid
     */
    void setParticipant(List<String> idList, int rid);
}
