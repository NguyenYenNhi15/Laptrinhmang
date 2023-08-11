import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    private static Connection con;

    private static void getDBConnection(String dbName, String userName,String password) {
        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
        String dbClass = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbUrl,userName,password);

        } catch (Exception e) {

        }

    }

    public static void main(String[] args) {
        getDBConnection("user", "root", "1qaz@123");
        System.out.println(con);
    }
}
