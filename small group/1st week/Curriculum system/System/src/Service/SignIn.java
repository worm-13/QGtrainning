package Service;

import Dao.CrudUtil;
import Dao.JDBC;
import Table.Student;
import Table.Teacher;

import java.sql.*;
import java.util.Scanner;

public class SignIn extends CrudUtil {
        static Scanner sc=new Scanner(System.in);

    public static boolean equal(String s1,String s2){
        if (s1 == null) {
            return s2 == null;
        }
        return s1.equals(s2);
    }

        public static Student login_student(Connection connection,int[] i) throws SQLException {
            String ID;//手机号
            String password;//密码
            String sql = "select * from student where ID=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            System.out.println("输入你的手机号:");
            ID = sc.nextLine();
            pstm.setString(1, ID);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                rs.getString("password");
                while (true) {
                    System.out.println("请输入密码:");
                    password = sc.nextLine();
                    if (equal(password, rs.getString("password"))) {
                        i[0]=10;
                        System.out.println("欢迎"+rs.getString("name"));
                        return new Student(
                                rs.getString("Class"),
                                rs.getString("name"),
                                rs.getString("ID"),
                                rs.getString("password")
                        );

                    } else {
                        System.out.println("密码错误！请重新输入,退出请按1，按其余任意数字键继续。");
                        int n = sc.nextInt();
                        sc.nextLine();
                        if (n == 1) {
                            break;
                        }
                    }

                }
            }else {
                System.out.println("用户不存在！！！");
            }
            rs.close();
            pstm.close();
            JDBC.returnConnection(connection);
            return null;
        }

    public static Teacher login_teacher(Connection connection, int[] i) throws SQLException {
        String ID;//手机号
        String password;//密码
        String sql = "select * from teacher where ID=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        System.out.println("输入你的手机号:");
        ID = sc.nextLine();
        pstm.setString(1, ID);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            rs.getString("password");
            while (true) {
                System.out.println("请输入密码:");
                password = sc.nextLine();
                if (equal(password, rs.getString("password"))) {
                    i[0]=10;
                    System.out.println("欢迎"+rs.getString("name"));
                    return new Teacher(
                            rs.getString("ID"),
                            rs.getString("name"),
                            rs.getString("password")
                    );

                } else {
                    System.out.println("密码错误！请重新输入,退出请按1，按其余任意数字键继续。");
                    int n = sc.nextInt();
                    sc.nextLine();
                    if (n == 1) {
                        break;
                    }
                }

            }
        }else {
            System.out.println("用户不存在！！！");
        }
        rs.close();
        pstm.close();
        JDBC.returnConnection(connection);
        return null;
    }

}





