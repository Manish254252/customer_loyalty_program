package in.stackroute;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static Connection connection;
    private static final String USER_NAME="root";
    private static final String PASSWORD="Admin123";
    private static final String URL="jdbc:mysql://localhost:3306/customer_loyalty";

    private DbConnection (){}

    public static Connection getInstance() throws NullPointerException, SQLException {
        if(connection!=null)
        {
            return connection;
        }
        // DriverManager.registerDriver(new Driver());
        connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        return connection;

    }

}

