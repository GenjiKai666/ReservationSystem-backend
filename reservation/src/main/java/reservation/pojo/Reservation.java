package reservation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("RESERVATION")
public class Reservation {
    @TableId(type = IdType.AUTO)
    Integer id;
    @TableField("USER_ID")
    Integer userId;
    @NotNull
    @TableField("FACILITY_ID")
    Integer facilityId;
    @NotNull
    @TableField("START_TIME")
    long startTime;
    @NotNull
    @TableField("END_TIME")
    long endTime;
    Integer status;
    public String getTime(){
        String start = new Date(startTime).toString();
        String end = new Date(endTime).toString();
        return start+"----->"+end;
    }
}
