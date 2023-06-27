package manage.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import manage.utils.Utils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilityVue {
    Integer id;
    String facilityName;
    Integer capacity;
    String location;
    String startTime;
    String endTime;
    String durationMin;
    String durationMax;
    public FacilityVue(Facility facility){
        this.id = facility.getId();
        this.facilityName = facility.getFacilityName();
        this.capacity = facility.getCapacity();
        this.location = facility.getLocation();
        this.startTime = Utils.long2hhmm(facility.getStartTime());
        this.endTime = Utils.long2hhmm(facility.getEndTime());
        this.durationMax = Utils.long2minutes(facility.getDurationMax());
        this.durationMin = Utils.long2minutes(facility.getDurationMin());
    }

}
