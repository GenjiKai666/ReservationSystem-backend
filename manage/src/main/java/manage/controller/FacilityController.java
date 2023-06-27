package manage.controller;

import manage.client.AuthClient;
import manage.pojo.Facility;
import manage.pojo.FacilityVue;
import manage.service.FacilityService;
import manage.utils.ConstantData;
import manage.utils.ResponseResult;
import manage.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
public class FacilityController {
    @Autowired
    AuthClient authClient;
    @Autowired
    FacilityService facilityService;

    @PostMapping("/manage/facility/insert")
    public ResponseResult<?> insertFacility(@RequestParam("operator") @NotBlank String operator,
                                            @RequestParam("token") @NotBlank String token,
                                            @RequestBody @Valid @NotNull Facility facility) {
        if (authClient.auth(operator, token, "admin").getData().equals(true)) {
            if (facilityService.insertFacility(facility)) {
                return new ResponseResult<>(ConstantData.CODE_NORMAL, "发布设施成功");
            } else {
                return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "发布设施失败");
            }
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "未通过认证");
        }
    }
    @PostMapping("/manage/facility/insert-vue")
    public ResponseResult<?> insertFacilityVue(@RequestParam("operator") @NotBlank String operator,
                                               @RequestParam("token") @NotBlank String token,
                                               @RequestBody Map<String,String> facility){
        Facility facilityInsert = new Facility();
        facilityInsert.setFacilityName(facility.get("facilityName"));
        facilityInsert.setCapacity(Integer.parseInt(facility.get("capacity")));
        facilityInsert.setLocation(facility.get("location"));
        facilityInsert.setStartTime(Utils.hhmm2long(facility.get("startTime")));
        facilityInsert.setEndTime(Utils.hhmm2long(facility.get("endTime")));
        facilityInsert.setDurationMin(Long.parseLong(facility.get("durationMin"))*60*1000L);
        facilityInsert.setDurationMax(Long.parseLong(facility.get("durationMax"))*60*1000L);
        return insertFacility(operator,token,facilityInsert);
    }
    @PostMapping("/manage/facility/update")
    public ResponseResult<?> updateFacility(@RequestParam("operator") @NotBlank String operator,
                                            @RequestParam("token") @NotBlank String token,
                                            @RequestBody @Valid @NotNull Facility facility) {
        if (authClient.auth(operator, token, "admin").getData().equals(true)) {
            if (facilityService.updateFacility(facility)) {
                return new ResponseResult<>(ConstantData.CODE_NORMAL, "发布设施成功");
            } else {
                return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "发布设施失败");
            }
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "未通过认证");
        }
    }
    @PostMapping("/manage/facility/update-vue")
    public ResponseResult<?> updateFacilityVue(@RequestParam("operator") @NotBlank String operator,
                                               @RequestParam("token") @NotBlank String token,
                                               @RequestBody Map<String,String> facility){
        Facility facilityInsert = new Facility();
        facilityInsert.setId(Integer.parseInt(facility.get("id")));
        facilityInsert.setFacilityName(facility.get("facilityName"));
        facilityInsert.setCapacity(Integer.parseInt(facility.get("capacity")));
        facilityInsert.setLocation(facility.get("location"));
        facilityInsert.setStartTime(Utils.hhmm2long(facility.get("startTime")));
        facilityInsert.setEndTime(Utils.hhmm2long(facility.get("endTime")));
        facilityInsert.setDurationMin(Long.parseLong(facility.get("durationMin"))*60*1000L);
        facilityInsert.setDurationMax(Long.parseLong(facility.get("durationMax"))*60*1000L);
        return updateFacility(operator,token,facilityInsert);
    }
    @PostMapping("/facilities")
    public ResponseResult<?> getFacilities(@RequestParam("operator") @NotBlank String operator,
                                           @RequestParam("token") @NotBlank String token,
                                           @RequestParam("type") @NotBlank String type){
        if (authClient.auth(operator, token, type).getData().equals(true)) {
            List<Facility> facilities = facilityService.getFacilities();
            return new ResponseResult<>(ConstantData.CODE_NORMAL,"获取信息成功",facilities);
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "未通过认证");
        }
    }
    @PostMapping("/facilities-vue")
    public ResponseResult<?> getFacilitiesVue(@RequestParam("operator") @NotBlank String operator,
                                              @RequestParam("token") @NotBlank String token,
                                              @RequestParam("type") @NotBlank String type){
        if (authClient.auth(operator, token, type).getData().equals(true)) {
            List<FacilityVue> facilities = facilityService.getFacilitiesVue();
            return new ResponseResult<>(ConstantData.CODE_NORMAL,"获取信息成功",facilities);
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "未通过认证");
        }
    }
    @PostMapping("/facility")
    public ResponseResult<?> getFacility(@RequestParam("operator") @NotBlank String operator,
                                         @RequestParam("token") @NotBlank String token,
                                         @RequestParam("type") @NotBlank String type,
                                         @RequestParam("facility_id") @NotNull Integer facilityId){
        if (authClient.auth(operator, token, type).getData().equals(true)) {
            Facility facility = facilityService.getFacility(facilityId);
            return new ResponseResult<>(ConstantData.CODE_NORMAL,"获取设施信息成功",facility);
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "未通过认证");
        }
    }
    @PostMapping("/facility_str")
    public ResponseResult<?> getFacility_str(@RequestParam("operator") @NotBlank String operator,
                                         @RequestParam("token") @NotBlank String token,
                                         @RequestParam("type") @NotBlank String type,
                                         @RequestParam("facilityName") @NotNull String facilityName){
        if (authClient.auth(operator, token, type).getData().equals(true)) {
            Facility facility = facilityService.getFacilityStr(facilityName);
            return new ResponseResult<>(ConstantData.CODE_NORMAL,"获取设施信息成功",facility);
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "未通过认证");
        }
    }
    @PostMapping("/facility/delete")
    public ResponseResult<?> deleteFacility(@RequestParam("operator") @NotBlank String operator,
                                            @RequestParam("token") @NotBlank String token,
                                            @RequestParam("type") @NotBlank String type,
                                            @RequestParam("facility_id") @NotNull Integer facilityId){
        if (authClient.auth(operator, token, type).getData().equals(true)) {
            if(facilityService.deleteFacility(facilityId)){
                return new ResponseResult<>(ConstantData.CODE_NORMAL,"删除成功");
            }
            else {
                return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED,"删除失败");
            }
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "未通过认证");
        }
    }
}
