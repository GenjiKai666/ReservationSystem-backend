package reservation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    @TableId(type = IdType.AUTO)
    Integer id;
    String username;
    String password;
    Integer status;
    Integer credit;
}
