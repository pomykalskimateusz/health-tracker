package health.tracker.repository;

import java.sql.*;
import java.util.List;

public class DatabaseConnector
{
    private static final String DRIVER_CLASS = "org.h2.Driver";
    private static final String JDBC_URL =  "jdbc:h2:mem:~/healthtracker;DB_CLOSE_DELAY=-1;";
    private static final String USER = "";
    private static final String PASSWORD = "";

    public void closeConnection(Connection connection, List<Statement> statements)
    {
        statements.forEach(this::closeStatement);

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

    public Connection prepareConnection() throws SQLException, ClassNotFoundException
    {
        Class.forName(DRIVER_CLASS);

        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    private void closeStatement(Statement statement)
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
    }
}

