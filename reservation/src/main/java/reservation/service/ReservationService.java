package reservation.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.mapper.ReservationMapper;
import reservation.pojo.Reservation;
import reservation.utils.ConstantData;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    ReservationMapper reservationMapper;

    public List<String> getReservationInfo(Integer facilityId,Long currentTime){
        List<Reservation> reservations = reservationMapper.selectList(Wrappers.lambdaQuery(Reservation.class)
                .eq(Reservation::getFacilityId,facilityId));
        return reservations.stream()
                .filter(x->x.getStartTime()>currentTime)
                .filter(x-> !Objects.equals(x.getStatus(), ConstantData.STATUS_CANCELED))
                .sorted(Comparator.comparing(Reservation::getStartTime))
                .map(Reservation::getTime)
                .collect(Collectors.toList());
    }
}
