package Table;

public class Course_select {
        private String Course_id;
        private String Student_id;

        public String getCourse_id() {
                return Course_id;
        }

        public void setCourse_id(String course_id) {
                Course_id = course_id;
        }

        public String getStudent_id() {
                return Student_id;
        }

        public void setStudent_id(String student_id) {
                Student_id = student_id;
        }

        //数据库中列名
        public static String[] column(){
                return new String[]{"Course_id","Student_id"};
        }
}
