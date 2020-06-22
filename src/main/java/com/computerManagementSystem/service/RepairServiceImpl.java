package com.computerManagementSystem.service;

import com.computerManagementSystem.mapper.RepairFormMapper;
import com.computerManagementSystem.pojo.Repair;
import com.computerManagementSystem.pojo.RepairForm;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赖志宇
 * @date 2020/6/17 23:39
 */

@Service("RepairService")
public class RepairServiceImpl implements RepairService,RepairFormDataService {
    @Resource
    private RepairFormMapper repairFormMapper;
    @Override
    public List<RepairForm> userQuerySelfRepair(int page, int limit, String phoneNumber) {
        int start = (page-1)*limit;
        Map<String,Object> attribute = new HashMap<>();
        attribute.put("start",start);
        attribute.put("limit",limit);
        attribute.put("phoneNumber",phoneNumber);
        return repairFormMapper.queryRepairFormByPhoneNumber(attribute);
    }

    @Override
    public List<RepairForm> memberQuerySelfTask(String id) {
        return repairFormMapper.queryRepairFormByMemberID(id);
    }

    @Override
    public int queryUserRepairNumsByPhoneNumber(String phoneNumber) {
        return  repairFormMapper.queryRepairNumsByPhoneNumber(phoneNumber);
    }

    @Override
    public List<RepairForm>queryRepairListByState(String state) {
      if(state.equals("'任务取消'or state = '已完成'"))
        return repairFormMapper.queryRepairFormByState(state,"repair_method != ''");
      return repairFormMapper.queryRepairFormByState(state,"repair_method = '线上报修'");
    }

    public List<RepairForm> queryOfflineTaskList(){
        return repairFormMapper.queryRepairFormByState("'未接取'","repair_method = '线下送修'");
    }
    @Override
    public void acceptRepair(RepairForm repairForm) {
       repairFormMapper.insertRepairForm(repairForm);
    }

    @Override
    public void modifyRepairStatus(int rid,String state) {
        RepairForm repairForm = new RepairForm();
        repairForm.setRid(rid);
        repairForm.setState(state);
        if(!"未完成".equals(state))
            repairForm.setEndDate(new Date(new java.util.Date().getTime()).toString());
        repairFormMapper.updateRepairForm(repairForm);
    }

    @Override
    public void setParticipant(List<String> idList, int rid) {
        //先根据vid delete掉repairs再循环插入
        repairFormMapper.deleteRepairsByRid(rid);
        Map<String,Object> attribute = new HashMap<>();
        attribute.put("rid",rid);
        for(int i=0;i<idList.size();i++){
            //最好判断一下id存不存在
            attribute.put("id",idList.get(i));
            repairFormMapper.setParticipant(attribute);
        }


    }
}
