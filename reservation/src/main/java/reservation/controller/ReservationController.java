package reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reservation.client.AuthClient;
import reservation.service.ReservationService;
import reservation.utils.ConstantData;
import reservation.utils.ResponseResult;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
public class ReservationController {
    @Autowired
    AuthClient authClient;
    @Autowired
    ReservationService reservationService;
    @PostMapping("/reservation/info")
    public ResponseResult<?> getReservationInfo(@RequestParam("operator") @NotBlank String operator,
                                                @RequestParam("token") @NotBlank String token,
                                                @RequestParam("type") @NotBlank String type,
                                                @RequestParam("facility_id") Integer facilityId){
        if (authClient.auth(operator, token, type).getData().equals(true)) {
            List<String> infos = reservationService.getReservationInfo(facilityId,System.currentTimeMillis());
            return new ResponseResult<>(ConstantData.CODE_NORMAL,"获取信息成功",infos);
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "未通过认证");
        }
    }
}
