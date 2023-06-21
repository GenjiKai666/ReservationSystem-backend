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
}
