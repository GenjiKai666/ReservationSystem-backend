package manage.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@TableName("FACILITY")
public class Facility {
    @TableId(type = IdType.AUTO)
    Integer id;

    @NotBlank
    @TableField("FACILITY_NAME")
    String facilityName;

    @Min(0)
    Integer capacity;

    @NotBlank
    String location;

    @Min(0)
    @Max(86399999)
    @TableField("START_TIME")
    Long startTime;

    @Min(0)
    @Max(86400000)
    @TableField("END_TIME")
    Long endTime;

    @Min(0)
    @Max(86399999)
    @TableField("DURATION_MIN")
    Long durationMin;

    @Min(0)
    @Max(86400000)
    @TableField("DURATION_MAX")
    Long durationMax;
}
