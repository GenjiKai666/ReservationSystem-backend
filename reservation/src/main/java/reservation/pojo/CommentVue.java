package reservation.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVue {
    Integer rate;
    String content;
    public CommentVue(Comment comment){
        this.rate = comment.getRate();
        this.content = comment.getContent();
    }
}
