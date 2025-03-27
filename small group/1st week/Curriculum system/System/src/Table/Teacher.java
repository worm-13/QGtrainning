package Table;

public class Teacher {
    private String name;
    private String ID;
    private String password;

    public Teacher(String ID, String name, String password) {
        this.ID = ID;
        this.name = name;
        this.password = password;
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
