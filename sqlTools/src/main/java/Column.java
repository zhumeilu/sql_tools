import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * User: zhumeilu
 * Date: 2018/1/10
 * Time: 19:17
 */
@Getter
@Setter
public class Column {

    private String table;       //表名
    private String column;      //列名
    private String alias;       //别名
}
