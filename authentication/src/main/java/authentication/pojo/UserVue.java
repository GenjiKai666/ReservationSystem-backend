package authentication.pojo;

import authentication.utils.ConstantData;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVue {
    Integer id;
    String username;
    String status;
    Integer credit;
    public UserVue(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        if (user.getStatus().equals(ConstantData.STATUS_NORMAL)){
            this.status = "正常";
        }
        else {
            this.status = "封禁";
        }
        this.credit = user.getCredit();
    }
}
