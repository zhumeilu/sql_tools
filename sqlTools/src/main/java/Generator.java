import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * User: zhumeilu
 * Date: 2018/1/10
 * Time: 17:21
 */
public class Generator {

    public static void main(String[] args) throws IOException {


        InputStream resourceAsStream = Generator.class.getClassLoader().getResourceAsStream("test.sql");
        BufferedReader in=new BufferedReader(new InputStreamReader(resourceAsStream));
        String sql = "";
        String columns = "";
        String tables = "";
        StringBuilder sb = new StringBuilder();
        String line=null;
        while((line=in.readLine())!=null){
            sb.append(" "+line);
        }
        sql = sb.toString();
        System.out.println(sql);
        String regex = "[SELECT,select]\\s(?<columns>.*?)\\s[FROM,from]";       //列名
        String regex2 = "[FROM,from]\\s(?<table>.*?)\\s[WHERE,where]";      //表名
        Pattern pattern =Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sql);
        if(matcher.find()){
            columns = matcher.group("columns");
            System.out.println(columns);
        }else{
            System.out.println("未发现列名");
        }
        Pattern pattern2 =Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(sql);
        if(matcher2.find()){
            tables = matcher2.group("table");
            System.out.println(tables);
        }else{
            System.out.println("未发现表名");
        }
        String[] colmunArray = columns.split(",");
        List<String> columnsList = new ArrayList<String>();
        for (String colnum:
        colmunArray) {
            columnsList.add(colnum);
        }
        String sql2 = "select DISTINCT column_name as column_name,data_type as data_type from information_schema.columns where table_name = '"+tables+"'";


        InputStream is = Generator.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = sessionFactory.openSession();

        CommonMapper mapper = session.getMapper(CommonMapper.class);
        List<Map> maps = mapper.commonSelect(sql2);
        System.out.println(maps);
        for (Map<String, String> map : maps) {
            String column_name = map.get("column_name");
            String data_type = map.get("data_type");
            if(columnsList.contains(column_name)){

            }
            System.out.println(column_name);
            System.out.println(data_type);

        }
    }
}
