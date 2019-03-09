package health.tracker.repository;

import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseConnector
{
    private static final String DRIVER_CLASS = "org.h2.Driver ";
    private static final String JDBC_URL =  "jdbc:h2:tcp://localhost/healthtracker";
    private static final String USER = "database_user";
    private static final String PASSWORD = "database_password";

    public void closeConnection(Connection connection, PreparedStatement statement)
    {
        if(statement != null)
        {
            try
            {
                statement.close();
            }
            catch(SQLException exception)
            {
                System.out.println("Error something wrong during closing connection.");
            }
        }
        if(connection != null)
        {
            try
            {
                connection.close();
            }
            catch(SQLException exception)
            {
                System.out.println("Error something wrong during closing connection.");
            }
        }
    }

    public Connection prepareConnection() throws Exception
    {
        Class.forName(DRIVER_CLASS);

        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
}

