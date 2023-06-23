package reservation.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.mapper.ReservationMapper;
import reservation.pojo.Facility;
import reservation.pojo.Reservation;
import reservation.utils.ConstantData;
import reservation.utils.Utils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    ReservationMapper reservationMapper;

    public List<Reservation> getReservationInfo(Integer facilityId, Long currentTime) {
        List<Reservation> reservations = reservationMapper.selectList(Wrappers.lambdaQuery(Reservation.class)
                .eq(Reservation::getFacilityId, facilityId));
        return reservations.stream()
                .filter(x -> x.getStartTime() > currentTime)
                .filter(x -> !Objects.equals(x.getStatus(), ConstantData.STATUS_CANCELED))
                .sorted(Comparator.comparing(Reservation::getStartTime))
                .collect(Collectors.toList());
    }

    public boolean reserve(Reservation reservation, Facility facility) {
        long start_relative = reservation.getStartTime() % 86400000;
        long end_relative = reservation.getEndTime() % 86400000;
        long duration = reservation.getEndTime() - reservation.getStartTime();
        long currentTime = System.currentTimeMillis();
        //只能预约一周之内的时间
        //开始及结束时间要在指定范围内
        //持续时间要在指定范围内
        if (reservation.getStartTime() - currentTime >= 604800000L
                || reservation.getStartTime() < currentTime
                || reservation.getStartTime() >= reservation.getEndTime()
                || start_relative <= facility.getStartTime()
                || end_relative >= facility.getEndTime()
                || duration < facility.getDurationMin()
                || duration > facility.getDurationMax()) {
            return false;
        } else {
            List<Reservation> reservations = getReservationInfo(reservation.getFacilityId(), System.currentTimeMillis());
            if (Utils.isOccupied(reservations, reservation)) {
                reservation.setStatus(ConstantData.STATUS_WAIT);
                reservationMapper.insert(reservation);
                return true;
            } else {
                return false;
            }
        }

    }
}
