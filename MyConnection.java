import java.sql.*;
public class MyConnection
{
    static Connection getConnection() throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/EcoMartPortal",
        "root",
        "Ruhi");
    }
}