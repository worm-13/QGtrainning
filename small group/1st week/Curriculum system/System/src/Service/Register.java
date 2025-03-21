package Service;

import View.Show;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Register {
    static Scanner sc=new Scanner(System.in);

    //设置学生注册时，sql插入的列名
    public static String[] student_colum(){
        String[] str={"Class","name","ID","password"};
        return str;
    }

    //学生注册，返回插入内容
    static public List student_register(String ID) {
        List<Object> str=new ArrayList<>();
        String Class=null;
        while (true){
        //选择班级
        System.out.println("请选择班级:");
        Show.register();
        int choice= sc.nextInt();
            switch (choice){
                case 1:
                    Class="1班";
                    break;
                case 2:
                    Class="2班";
                    break;
                case 3:
                    Class="3班";
                    break;
                case 4:
                    Class="4班";
                    break;
                case 5:
                    Class="5班";
                    break;
                case 6:
                    Class="6班";
                    break;
                default:
                System.out.println("无效选项，请重新选择!");
                continue;
            }
            break;//输入有效，直接跳
        }
            sc.nextLine();//吃个回车
            //输入姓名
            System.out.println("请输入姓名:");
            String name=sc.nextLine();
            //输入id
            System.out.println("请输入手机号:");
            ID=sc.nextLine();
            System.out.println("手机号已注册！！！");
            //输入密码
            System.out.println("请输入密码:");
            String password = sc.nextLine();
            str.add(Class);
            str.add(name);
            str.add(ID);
            str.add(password);
            System.out.println("注册成功！");
            return str;


    }

    //如法炮制学生注册
    static public List<Object> teacher_register(String ID){
            List<Object> str=new ArrayList<>();
            //输入姓名
            System.out.println("请输入姓名:");
            String name=sc.nextLine();
            //输入密码
            System.out.println("请输入密码:");
            String password=sc.nextLine();
            //加入队列
            str.add(name);
            str.add(ID);
            str.add(password);
            System.out.println("注册成功！");
            return str;
    }

    public static String[] teacher_colum(){
        String[] str={"name","ID","password"};
        return str;
    }
}
