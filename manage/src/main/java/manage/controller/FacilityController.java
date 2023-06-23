package manage.controller;

import manage.client.AuthClient;
import manage.pojo.Facility;
import manage.service.FacilityService;
import manage.utils.ConstantData;
import manage.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

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
}
