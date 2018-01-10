import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 * User: zhumeilu
 * Date: 2018/1/10
 * Time: 18:39
 */
public interface CommonMapper {

    List commonSelect(@Param("sql") String sql);
}
