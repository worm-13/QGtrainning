package View;

import Dao.CrudUtil;
import Dao.JDBC;
import Service.Register;
import Service.SignIn;
import Table.Course_select;
import Table.Student;
import Table.Teacher;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Show {
    static Scanner sc=new Scanner(System.in);

    public static void register(){
        System.out.println("1: 1班              4: 4班");
        System.out.println("2: 2班              5: 5班");
        System.out.println("3: 3班              6：6班");
    }

    public static void menu() throws SQLException{
        CrudUtil crudUtil=new CrudUtil();
        int i;
        while (true){
        System.out.println("""
                ===========================
                 学生选课管理系统
                ===========================
                1. 登录
                2. 注册
                3. 退出
                请选择操作（输入 1-3）：""");
        i=sc.nextInt();
        int a=0;
        switch (i){
            case 1:
                menu1(crudUtil);
                break;
            case 2:
                menu2();
                break;
            case 3:
                System.out.println("已退出系统,感谢使用！！！");
                a=1;
                break;
            default:
                System.out.println("无效选项，请重新选择!");
                continue;
        }
        if (a==1){
            break;
        }
    }
    }

    public static void menu1(CrudUtil crudUtil) throws SQLException{
        int i;
        int[] key={0};
        while (true){
            System.out.println("""
                    ========================
                    登录
                    选择你的身份:
                    1.学生
                    2.老师
                    3.返回
                    ========================
                    """);
            i=sc.nextInt();
            switch (i){
                case 1:
                    Student student= SignIn.login_student(JDBC.getConnection(),key);
                    if (key[0]==10){
                        menu1_1(crudUtil,student);
                    }
                    break;
                case 2:
                    Teacher teacher=SignIn.login_teacher(JDBC.getConnection(),key);
                    if (key[0]==10){
                        menu1_2(crudUtil,teacher);
                    }
                    break;
                case 3:
                    i=100;
                    break;
                default:
                    System.out.println("无效选项，请重新选择:");
                    continue;
            }
            if (i==100){
                break;
            }
        }


    }

    public static void menu1_1(CrudUtil crudUtil,Student student ) throws SQLException{
        int i;
        while (true) {
            System.out.println("""
                    ===== 学生菜单 =====
                    1. 查看可选课程
                    2. 选择课程
                    3. 退选课程
                    4. 查看已选课程
                    5. 退出
                    请选择操作（输入 1-6）：
                    """);
            i=sc.nextInt();
            sc.nextLine();
            int check=0;
            int select=0;
            switch (i){
                case 1:
                    crudUtil.QueryCourse("course",JDBC.getConnection());
                    break;
                case 2:
                    crudUtil.QueryCourse("course",JDBC.getConnection());
                    String choice;
                    while (true) {
                        System.out.println("请输入可选课程id以选课:");
                        choice = sc.nextLine();
                        if (crudUtil.checkPeopleDao(choice,JDBC.getConnection())&&(crudUtil.query_check("course","Course_ID",choice,JDBC.getConnection()))){
                            List<Object> id=new ArrayList<>();
                            id.add(choice);
                            id.add(student.getID());
                            if (crudUtil.insertDao("course_select", Course_select.column(),id ,JDBC.getConnection())==0)
                            {
                                Integer people=crudUtil.getPeople(choice,JDBC.getConnection());
                                people-=1;
                                Object object=people;
                                crudUtil.updateDao("course","people","Course_ID",object,choice,JDBC.getConnection());
                                System.out.println("选课成功！！！");
                            }else{
                                System.out.println("你已选择该课程！！！");
                            }
                            check=1;
                        }else {
                            choice=null;
                            System.out.println("此课程已无名额或课程不存在！！！按任意数字键重新选择，退出请按2：");
                            select=sc.nextInt();
                            sc.nextLine();// 吃掉回车
                        }
                        if (check==1){
                            break;
                        }
                        if (select==2){
                            break;
                        }
                    }
                    break;
                case 3:
                    System.out.println("用户 "+student.getName()+" 已选课程:");
                    crudUtil.queryCourseSelect(crudUtil.getCourseSelect_Dao(student.getID(),JDBC.getConnection()),JDBC.getConnection());// 展示所选课程
                    System.out.println("请输入你要退选的课程id：");
                    String id=sc.nextLine();
                    crudUtil.dropCourseSelect(id,JDBC.getConnection());// 删除选择信息
                    Integer people=crudUtil.getPeople(id,JDBC.getConnection());
                    people+=1;
                    Object object=people;
                    crudUtil.updateDao("course","people","Course_ID",object,id,JDBC.getConnection());// 退课更新名额
                    System.out.println("退课成功");
                    break;
                case 4:
                    System.out.println("用户 "+student.getName()+" 已选课程:");
                    crudUtil.queryCourseSelect(crudUtil.getCourseSelect_Dao(student.getID(),JDBC.getConnection()),JDBC.getConnection());// 展示所选课程
                    System.out.println();
                    break;
                case 5:
                    i=9;
                    System.out.println("账户已退出。");
                    break;
                default:
                    System.out.println("选项无效！请重新输入。");
                    continue;
            }
            if (i==9){
                break;
            }
        }
    }

    public static void menu1_2(CrudUtil crudUtil,Teacher teacher)throws SQLException{
        int i;
        int select=0;
        String course_id;
        String student_id;
        int score;
        while (true) {
            System.out.println("""
                    ===== 管理员菜单 =====
                    1. 查询所有学生
                    2. 修改学生手机号
                    3. 查询所有课程
                    4. 修改课程学分
                    5. 查询某课程的学生名单
                    6. 查询某学生的选课情况
                    7. 退出
                    请选择操作（输入 1-7）：
                    """);
            i=sc.nextInt();
            switch (i){
                case 1:
                    crudUtil.QueryStudent("student",JDBC.getConnection());
                    break;
                case 2:
                    break;
                case 3:
                    crudUtil.QueryCourse("course",JDBC.getConnection());
                    break;
                case 4:
                    System.out.println("请输入您要修改的课程id:");
                    course_id=sc.nextLine();
                    sc.nextLine();// 吃掉回车
                    System.out.println("输入要修改成的分数:");
                    score=sc.nextInt();
                    crudUtil.updateScore(JDBC.getConnection(),score,course_id);
                    System.out.println("更改成功。");
                    break;
                case 5:
                    sc.nextLine();
                    System.out.println("请输入想要查询课程的id：");
                    course_id=sc.nextLine();
                    crudUtil.queryStudentSelect(crudUtil.getStudentSelect_Dao(course_id,JDBC.getConnection()),JDBC.getConnection());
                case 6:
                    sc.nextLine();
                    System.out.println("请输入想要查询学生的手机号：");
                    student_id=sc.nextLine();
                    crudUtil.queryCourseSelect(crudUtil.getCourseSelect_Dao(student_id,JDBC.getConnection()),JDBC.getConnection());
                case 7:
                    select=10;
                    break;

            }
            if (select==10){
                break;
            }
        }
    }

    //注册选项
    public static void menu2() throws SQLException {
        int i;
        String ID;
        CrudUtil crudUtil =new CrudUtil();
        while (true) {
            System.out.println("""
                    ========================
                    选择你的身份:
                    1.学生
                    2.老师
                    3.返回
                    ========================
                    """);
            i=sc.nextInt();

            switch (i) {
                case 1:
                    sc.nextLine();
                    System.out.println("请输入手机号:");
                    ID=sc.nextLine();
                    // 查重，若手机号已注册则返回
                    if (crudUtil.query_check("student","ID",ID,JDBC.getConnection())||crudUtil.query_check("teacher","ID",ID,JDBC.getConnection())){
                        System.out.println("手机号已被注册！");
                    }else {
                        crudUtil.insertDao("student", Register.student_colum(), Register.student_register(ID),JDBC.getConnection()) ;
                    }

                    break;
                case 2:
                    sc.nextLine();
                    System.out.println("请输入手机号:");
                    ID=sc.nextLine();
                    // 查重
                    if (crudUtil.query_check("teacher","ID",ID,JDBC.getConnection())||crudUtil.query_check("student","ID",ID,JDBC.getConnection())){
                        System.out.println("手机号已被注册！");
                    }else {
                        crudUtil.insertDao("teacher", Register.teacher_colum(), Register.teacher_register(ID), JDBC.getConnection());
                    }
                    break;
                case 3:
                    break;
                default:
                    System.out.println("无效选项，请重新选择:");
                    continue;
            }
            JDBC.returnConnection(JDBC.getConnection());
            break;
        }
    }


}
