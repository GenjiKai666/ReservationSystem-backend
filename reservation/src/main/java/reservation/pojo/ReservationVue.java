package reservation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationVue {
    Integer id;
    String facilityName;
    String time;
    String status;
    public ReservationVue(Reservation reservation,Facility facility){
        this.id = reservation.getId();
        this.facilityName = facility.getFacilityName();
        this.time = reservation.getTime();
        switch (reservation.getStatus()){
            case 0:
                this.status = "待处理";
                break;
            case 1:
                this.status = "待签到";
                break;
            case 2:
                this.status = "已签到";
                break;
            case 3:
                this.status = "爽约";
                break;
            case 4:
                this.status ="已取消";
                break;
        }
    }
}
