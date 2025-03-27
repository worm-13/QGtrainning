import Dao.JDBC;
import View.Show;
import java.sql.SQLException;

public class Main {
    static {
        try {
            JDBC.list();
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }


    public static void main(String[] args) throws SQLException {
        Show.menu();

    }


}