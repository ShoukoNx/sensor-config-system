package sensor.component;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * 数据库链接类
 *
 * @author ysr
 */
public class Database {
    private static final String DB_URL ="jdbc:mysql://localhost:3306/sensor?characterEncoding=utf-8";
    private static final String DB_USER ="root";
    private static final String DB_PASSWORD ="12345678";
    private static final String JDBC_CLASS ="com.mysql.cj.jdbc.Driver";

    public Connection getCon() throws Exception{
        Class.forName(JDBC_CLASS);
        Connection con= DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        return con;
    }

    //关闭数据库
    public void closeConnection(Connection con) throws Exception{
        if(con!=null){
            con.close();
        }
    }

    public static void main(String[] args) throws Exception {
        Database db=new Database();
        try{
            db.getCon();
            System.out.println("数据库连接成功");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }
    }
}
