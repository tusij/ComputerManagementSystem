package com.computerManagementSystem.service;

import com.computerManagementSystem.pojo.RepairForm;

import java.util.List;
import java.util.Map;

/**
 * 用于维修相关业务
 * 本接口更加关注数据的查询返回
 * @author 赖志宇
 * @date 2020/6/17 2:24
 */
public interface RepairFormDataService {
    List<RepairForm> userQuerySelfRepair(int page, int limit, String phoneNumber);

    List<RepairForm> memberQuerySelfTask( String id);

    int queryUserRepairNumsByPhoneNumber(String phoneNumber);

    List<RepairForm> queryRepairListByState(String state);
}
