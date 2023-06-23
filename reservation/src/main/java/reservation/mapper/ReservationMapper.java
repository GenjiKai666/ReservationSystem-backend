package reservation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import reservation.pojo.Reservation;

@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {
}
