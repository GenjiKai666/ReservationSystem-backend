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
        String[] start = new Date(startTime).toString().split(" ");
        String[] end = new Date(endTime).toString().split(" ");
        String start_str = start[1]+" "+start[2]+" "+start[3]+" "+start[5];
        String end_str = end[1]+" "+end[2]+" "+end[3]+" "+end[5];
        return start_str+"至"+end_str;
    }
}
