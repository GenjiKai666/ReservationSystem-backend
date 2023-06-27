package reservation.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    Integer avgRate;
    List<String> reservationInfo;
    List<CommentVue> commentInfo;
}
