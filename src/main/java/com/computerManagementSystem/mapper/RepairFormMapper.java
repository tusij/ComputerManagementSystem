package com.computerManagementSystem.mapper;

import com.computerManagementSystem.pojo.RepairForm;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * @author 赖志宇
 * @date 2020/6/17 2:21
 */

@Mapper
public interface RepairFormMapper {

    void insertRepairForm(RepairForm repairForm);

    void updateRepairForm(RepairForm repairForm);

    //报修用户查询报修历史用
    List<RepairForm> queryRepairFormByPhoneNumber(Map<String, Object> attribute);

    //接线员管理员管理任务界面，按完成状态分类
    List<RepairForm> queryRepairFormByState(@Param("state") String state,@Param("repairMethod") String repairMethod);

    //成员查询自己所接的任务用
    List<RepairForm> queryRepairFormByMemberID( String id);

    int queryRepairNumsByPhoneNumber(String phoneNumber);

    @Delete("delete from repairs where rid = #{rid};")
    void deleteRepairsByRid(int rid);

    void setParticipant(Map<String,Object> attribute);
}
