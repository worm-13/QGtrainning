package Dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;

public class JDBC {
    static public final Queue<Connection> connectionPool=new LinkedList<>();
    static private final int Size=20;

    //读取文件，获取mysql信息
    public static List<String> input(){
        Properties properties=new Properties();
        try {
            FileInputStream input = new FileInputStream("C:\\Users\\15510\\Desktop\\Curriculum system\\System\\resource\\MySQL.properties");
            properties.load(input);
        }catch (Exception E){
            System.out.println(E);
        }
        String user=properties.getProperty("user");
        String url=properties.getProperty("url");
        String password=properties.getProperty("password");
        List<String> str=new LinkedList<>();
        str.add(url);
        str.add(user);
        str.add(password);
        return str;
    }

    //创造连接的方法
    public static Connection connection(List<String> strings) throws SQLException{
        Connection connection=DriverManager.getConnection(strings.get(0),strings.get(1),strings.get(2));
        return connection;
    }

    /**
     将连接添加进连接池
     */
    public static void list() throws SQLException{
        List<String> strings=input();
        for (int i = 0; i < Size; i++) {
            Connection connection=connection(strings);
            connectionPool.add(connection);
        }
    }

    //从连接池获取连接
    public static synchronized Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            throw new SQLException("连接池为空！");
        }
        return connectionPool.poll();
    }

    public static void returnConnection(Connection connection) {
        if (connection != null) {
            connectionPool.add(connection);
        }
    }
}
