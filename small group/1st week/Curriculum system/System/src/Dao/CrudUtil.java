package Dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrudUtil {

    // 注册插入功能
    public int insertDao(String tableName, String[] columns, List<Object> values, Connection conn) {
        if (columns == null || values == null || columns.length != values.size()) {
            throw new IllegalArgumentException("列名和值的数量必须一致");
        }
        PreparedStatement pstm = null;
        try {
            // 构建SQL语句
            StringBuilder sql = new StringBuilder("INSERT INTO ");
            sql.append(tableName).append(" (");
            // 先添加列名，拼逗号
            for (int i = 0; i < columns.length; i++) {
                sql.append(columns[i]);
                if (i < columns.length - 1) {
                    sql.append(", ");
                }
            }
            sql.append(") VALUES (");
            // 再添加内容，设置占位符
            for (int i = 0; i < values.size(); i++) {
                sql.append("?");
                if (i < values.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(")");
            // sql是StringBuilder类，需要转化为String类
            pstm = conn.prepareStatement(sql.toString());
            //设置占位符的参数
            for (int i = 0; i < values.size(); i++) {
                pstm.setObject(i + 1, values.get(i));
            }
            // 执行插入
            pstm.executeUpdate();
        } catch ( SQLException e) {
            System.out.println(e);
            return 1;
        } finally {
            // 关闭资源
            try {
                pstm.close();
                JDBC.returnConnection(conn);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return 0;
    }

    // 选课后更新名额
    public void updateDao(String tableName,String column1,String column2,Object values,String condition,Connection connection)throws SQLException{
        String sql="update "+tableName+" set "+column1+"=? where "+column2+"=?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setObject(1,values);
        pstm.setString(2,condition);
        pstm.executeUpdate();
        pstm.close();
        JDBC.returnConnection(connection);// 将连接返回连接池
    }

    public void updateScore(Connection connection,int value,String course_id)throws SQLException{
        String sql="update course set score=? where Course_id=?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1,value);
        pstm.setString(2,course_id);
        pstm.executeUpdate();
        pstm.close();
        JDBC.returnConnection(connection);
    }

    // 获取所选课程id
    public List<String> getCourseSelect_Dao(String studen_id,Connection connection)throws SQLException{
        String courseID;
        List<String> str=new ArrayList<>();
        String sql="select *from course_select where Student_id=?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setString(1,studen_id);
        ResultSet rs= pstm.executeQuery();
        while (rs.next()){
            courseID=rs.getString("Course_id");
            str.add(courseID);
        }
        pstm.close();
        rs.close();
        JDBC.returnConnection(connection);
        return str;
    }

    // 打印所选课程
    public void queryCourseSelect(List<String> course_id,Connection connection)throws SQLException{
        StringBuilder sql = new StringBuilder("SELECT * FROM course WHERE Course_ID IN (");

        // 根据情况生成占位符
        for (int i = 0; i < course_id.size(); i++) {
            sql.append("?");
            if (i < course_id.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(")");
        PreparedStatement pstm=connection.prepareStatement(sql.toString());
        for (int i = 0; i < course_id.size(); i++) {
            pstm.setString(i+1,course_id.get(i));
        }
        ResultSet rs= pstm.executeQuery();
        while (rs.next()){
            System.out.println(
                    rs.getString("name") + " (" + rs.getString("score") + "分) " +
                            rs.getString("time") + "  ID: " + rs.getString("Course_ID")
            );
        }
        pstm.close();
        rs.close();
        JDBC.returnConnection(connection);
    }

    // 获取学生id
    public List<String> getStudentSelect_Dao(String course_id,Connection connection)throws SQLException{
        String Student_id;
        List<String> str=new ArrayList<>();
        String sql="select *from course_select where Course_id=?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setString(1,course_id);
        ResultSet rs= pstm.executeQuery();
        while (rs.next()){
            Student_id=rs.getString("Student_id");
            System.out.println(Student_id);
            str.add(Student_id);
        }
        pstm.close();
        rs.close();
        JDBC.returnConnection(connection);
        return str;
    }

    // 打印课程对应学生姓名
    public void queryStudentSelect(List<String> student_id,Connection connection)throws SQLException{
        StringBuilder sql = new StringBuilder("SELECT * FROM student WHERE ID IN (");
        // 根据情况生成占位符
        for (int i = 0; i < student_id.size(); i++) {
            sql.append("?");
            if (i < student_id.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(")");
        PreparedStatement pstm=connection.prepareStatement(sql.toString());
        for (int i = 0; i < student_id.size(); i++) {
            pstm.setString(i+1,student_id.get(i));
        }
        ResultSet rs= pstm.executeQuery();
        while (rs.next()){
            System.out.println(
                    "班级："+rs.getString("Class")+
                    "   姓名："+rs.getString("name")
            );
        }
        pstm.close();
        rs.close();
        JDBC.returnConnection(connection);
    }

    // 退选课程
    public void dropCourseSelect(String id,Connection connection)throws SQLException{
        String sql="delete from course_select WHERE Course_ID=?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setString(1,id);
        pstm.executeUpdate();
        pstm.close();
        JDBC.returnConnection(connection);
    }


    // 检查课程是否可选
    public boolean checkPeopleDao(String id,Connection connection) throws SQLException{
        String sql="select *from course where Course_ID=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        ResultSet rs=preparedStatement.executeQuery();
        if (rs.next()) {
            int people = rs.getInt("people");
            if (people == 0) {
                return false;
            }
            preparedStatement.close();
            rs.close();
        }
        JDBC.returnConnection(connection);
        return true;
    }

    // 获取课程名额
    public int getPeople(String id,Connection connection)throws SQLException{
        String sql="select *from course where Course_ID=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        ResultSet rs=preparedStatement.executeQuery();
        if (rs.next()) {
            int people = rs.getInt("people");
            return people;

        }
        preparedStatement.close();
        rs.close();
        JDBC.returnConnection(connection);
        return 0;
    }

    // 查重方法
    public  boolean query_check(String tableName,String column,String values,Connection connection){
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + column + " = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,values);
            ResultSet rs=pstm.executeQuery();
            if(rs.next()){
                int count=rs.getInt(1);
                return count>0;
            }
            rs.close();
            pstm.close();
        }catch (SQLException E)
        {
            System.out.println(E);
        } finally {
            JDBC.returnConnection(connection);
        }
        return false;
    }

    //查看课程
    public static void QueryCourse(String tableName, Connection connection) throws SQLException {
        String sql="SELECT * FROM "+tableName;

        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet rs=pstm.executeQuery();

        while (rs.next()){
            System.out.printf("课程：1%s（%d分）    ID：%s     时间：%s   剩余名额：%d人\n",rs.getString("name"),rs.getInt("score"),rs.getString("Course_ID"),rs.getString("time"),rs.getInt("people"));
        }
        System.out.println();
        pstm.close();
        rs.close();
        JDBC.returnConnection(connection);
    }

    public static void QueryStudent(String tableName, Connection connection) throws SQLException {
        String sql="SELECT * FROM "+tableName;

        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet rs=pstm.executeQuery();

        while (rs.next()){
            System.out.println("班级："+rs.getString("Class")+"  姓名："+rs.getString("name")+"  手机号"+rs.getString("ID"));
        }
        System.out.println();
        pstm.close();
        rs.close();
        JDBC.returnConnection(connection);
    }




}
