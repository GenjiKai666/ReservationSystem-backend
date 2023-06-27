package reservation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import reservation.pojo.Comment;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
