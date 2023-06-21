package authentication.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("ADMIN")
public class Admin {
    @TableId(type = IdType.AUTO)
    Integer id;
    String username;
    String password;
    public Admin(String username,String password){
        this.username = username;
        this.password = password;
    }
}
