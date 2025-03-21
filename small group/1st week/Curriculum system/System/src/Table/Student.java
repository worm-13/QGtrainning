package Table;

public class Student {
    private String class1;
    private String name;
    private String ID;
    private String password;


    public Student(String class1,String name,String ID,String password){
        this.class1=class1;
        this.ID=ID;
        this.name=name;
        this.password=password;
    }

    public String getClass1(){
        return class1;
    }

    public void setClass(String class1) {
        this.class1 = class1;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
