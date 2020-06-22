package com.computerManagementSystem.controller;

import com.computerManagementSystem.pojo.Repair;
import com.computerManagementSystem.pojo.RepairForm;
import com.computerManagementSystem.service.RepairService;
import com.computerManagementSystem.service.RepairServiceImpl;
import com.computerManagementSystem.util.CookieUtil;
import com.computerManagementSystem.util.JWTUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赖志宇
 * @date 2020/6/18 2:11
 */

@RequestMapping(value = "member",method = RequestMethod.POST)
@RestController
public class RepairController {
    @Resource(name = "RepairService")
    private RepairServiceImpl repairService;

    //后期可以考虑把获取线下报修的方法合并到里面，主要思路是利用sql注入将state继续sql扩充
    @PostMapping(value = { "getWaitingTaskList", "getDoingTaskList", "getMembersTaskList"})
    public Map<String, Object> sendTaskList(HttpServletRequest httpServletRequest) throws IOException {
        String url = httpServletRequest.getServletPath();
        Map<String, String> urlMap = new HashMap<String, String>() {
            {
                put("/member/getWaitingTaskList", "'未接取'");
                put("/member/getDoingTaskList", "'未完成'");
                put("/member/getMembersTaskList", "'任务取消'or state = '已完成'");
            }

        };
        List<RepairForm> repairFormList = repairService.queryRepairListByState(urlMap.get(url));
        Map<String, Object> res= new HashMap<>();
        res.put("code", 0);
        res.put("count", repairFormList.size());
        res.put("data", repairFormList);
        res.put("msg", "");
        return res;
    }

    @PostMapping("getOfflineTaskList")
    public Map<String,Object> sendOfflineTaskList(){
        List<RepairForm> repairFormList = repairService.queryOfflineTaskList();
        Map<String, Object> res= new HashMap<>();
        res.put("code", 0);
        res.put("count", repairFormList.size());
        res.put("data", repairFormList);
        res.put("msg", "");
        return res;
    }
    @PostMapping("getMyTaskList")
    public Map<String,Object> getMyTaskList(HttpServletRequest request){
        String token = CookieUtil.getCookieValueNamedToken(request);
        String id = JWTUtil.getClaimNamedAccount(token);
        List<RepairForm> repairFormList = repairService.memberQuerySelfTask(id);
        Map<String, Object> res= new HashMap<>();
        res.put("code", 0);
        res.put("count", repairFormList.size());
        res.put("data", repairFormList);
        res.put("msg", "");
        return res;

    }
    @PostMapping("releaseTask")
    public void releaseTask(@RequestParam(value = "rid")int rid,@RequestParam(value = "idList") List<String> idList){

        repairService.setParticipant(idList,rid);
        repairService.modifyRepairStatus(rid,"未完成");
    }

    @PostMapping("finishOfflineTask")
    public void finishOfflineTask(@RequestParam(value = "rid")int rid,@RequestParam(value = "idList") List<String> idList){

        repairService.setParticipant(idList,rid);
        repairService.modifyRepairStatus(rid,"已完成");
    }

    @PostMapping(value = {"finishTask","cancelTask"})
    public void modifyTaskStatus(HttpServletRequest request,@RequestParam(value = "rid") int rid){
        String url = request.getServletPath();
        Map<String, String> urlMap = new HashMap<String, String>() {
            {
                put("/member/finishTask", "已完成");
                put("/member/cancelTask", "任务取消");
            }

        };
        repairService.modifyRepairStatus(rid,urlMap.get(url));

    }

}
