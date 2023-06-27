package reservation.controller;

import org.apache.commons.lang.time.DateUtils;
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
import reservation.pojo.ReservationVue;
import reservation.pojo.User;
import reservation.service.ReservationService;
import reservation.utils.ConstantData;
import reservation.utils.ResponseResult;
import reservation.utils.Utils;

import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    @PostMapping("/reservations/info")
    public ResponseResult<?> getReservations(@RequestParam("operator") @NotBlank String operator,
                                             @RequestParam("token") @NotBlank String token,
                                             @RequestParam("type") @NotBlank String type,
                                             @RequestParam("username") String username) {
        if (authClient.auth(operator, token, type).getData().equals(true)) {
            if (type.equals("user") && !operator.equals(username)){
                return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED,"查询失败");
            }
            User user = authClient.getUserInfo(operator,token,type,username).getData();
            List<ReservationVue> infos = reservationService.getReservations(user.getId())
                    .stream()
                    .map(x -> new ReservationVue(x, facilityClient.getFacility(operator, token, type, x.getFacilityId()).getData()))
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
            Facility facility = facilityClient.getFacility(operator, token, type, reservation.getFacilityId()).getData();
            if (currentUser.getStatus() == 1 || currentUser.getCredit() <= 60) {
                return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "您已被拉黑或信用分过低");
            } else {
                reservation.setUserId(currentUser.getId());
                if (reservationService.reserve(reservation, facility)) {
                    return new ResponseResult<>(ConstantData.CODE_NORMAL, "预定成功");
                } else {
                    return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "预定失败");
                }
            }
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "未通过认证");
        }
    }

    @PostMapping("/reserve-vue")
    public ResponseResult<?> reserveVue(@RequestParam("operator") @NotBlank String operator,
                                        @RequestParam("token") @NotBlank String token,
                                        @RequestParam("type") @NotBlank String type,
                                        @RequestBody Map<String, String> reservation) throws ParseException {
        String date = reservation.get("date").split("T")[0];
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        Long dateTime = dtf.parse(date).getTime();
        Long startTime = dateTime + 86400000L + Utils.hhmm2long(reservation.get("startTime"));
        Long endTime = dateTime + 86400000L + Utils.hhmm2long(reservation.get("endTime"));
        Reservation reservationInsert = new Reservation();
        reservationInsert.setFacilityId(Integer.parseInt(reservation.get("facilityId")));
        reservationInsert.setStartTime(startTime);
        reservationInsert.setEndTime(endTime);
        return reserve(operator, token, type, reservationInsert);
    }

    @PostMapping("/reservations")
    public ResponseResult<?> getRawReservations(@RequestParam("operator") @NotBlank String operator,
                                                @RequestParam("token") @NotBlank String token,
                                                @RequestParam("type") @NotBlank String type) {
        if (authClient.auth(operator, token, type).getData().equals(true)) {
            List<Reservation> rawReservations = reservationService.getRawReservations();
            return new ResponseResult<>(ConstantData.CODE_NORMAL, "获取待处理订单成功", rawReservations);
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "获取失败");
        }
    }
}
