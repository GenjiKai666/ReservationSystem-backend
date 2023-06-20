package authentication.pojo;

import authentication.utils.ConstantData;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("USER")
public class User {
    @TableId(type = IdType.AUTO)
    Integer id;
    String username;
    String password;
    Integer status;
    Integer credit;
    public User(String username,String password){
        this.username = username;
        this.password = password;
        status = ConstantData.STATUS_NORMAL;
        credit = ConstantData.CREDIT_INITIAL;
    }
}
