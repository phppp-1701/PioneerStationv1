package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static Connection con = null;
    private static ConnectDB instance = new ConnectDB();

    private ConnectDB() {} 

    public static ConnectDB getInstance() {
        return instance;
    }

    public void connect() throws SQLException {
        if (con == null || con.isClosed()) { 
            String url = "jdbc:sqlserver://localhost:1433;databaseName=pioneer_station";
            String user = "sa";
            String password = "sapassword";
            con = DriverManager.getConnection(url, user, password);
        }
    }

    public void disconnect() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Đã đóng kết nối!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) { 
            getInstance().connect(); 
            System.out.println("Đã mở kết nối!");
        }
        return con;
    }
}
