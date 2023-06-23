package reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reservation.client.AuthClient;
import reservation.client.FacilityClient;
import reservation.pojo.Facility;
import reservation.pojo.Reservation;
import reservation.pojo.User;
import reservation.service.ReservationService;
import reservation.utils.ConstantData;
import reservation.utils.ResponseResult;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReservationController {
    @Autowired
    AuthClient authClient;
    @Autowired
    FacilityClient facilityClient;
    @Autowired
    ReservationService reservationService;

    @PostMapping("/reservation/info")
    public ResponseResult<?> getReservationInfo(@RequestParam("operator") @NotBlank String operator,
                                                @RequestParam("token") @NotBlank String token,
                                                @RequestParam("type") @NotBlank String type,
                                                @RequestParam("facility_id") Integer facilityId) {
        if (authClient.auth(operator, token, type).getData().equals(true)) {
            List<String> infos = reservationService.getReservationInfo(facilityId, System.currentTimeMillis())
                    .stream()
                    .map(Reservation::getTime)
                    .collect(Collectors.toList());
            return new ResponseResult<>(ConstantData.CODE_NORMAL, "获取信息成功", infos);
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "未通过认证");
        }
    }

    @PostMapping("/reserve")
    public ResponseResult<?> reserve(@RequestParam("operator") @NotBlank String operator,
                                     @RequestParam("token") @NotBlank String token,
                                     @RequestParam("type") @NotBlank String type,
                                     @RequestBody @Validated Reservation reservation) {
        if (authClient.auth(operator, token, type).getData().equals(true)) {
            User currentUser = authClient.getUserInfo(operator, token, type, operator).getData();
            Facility facility = facilityClient.getFacility(operator,token,type, reservation.getFacilityId()).getData();
            if (currentUser.getStatus() == 1 || currentUser.getCredit() <= 60) {
                return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "您已被拉黑或信用分过低");
            } else {
                reservation.setId(currentUser.getId());
                if (reservationService.reserve(reservation,facility)){
                    return new ResponseResult<>(ConstantData.CODE_NORMAL,"预定成功");
                }
                else {
                    return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED,"预定失败");
                }
            }
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "未通过认证");
        }
    }
}
