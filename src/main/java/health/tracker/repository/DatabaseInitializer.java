package health.tracker.repository;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Component
public class DatabaseInitializer
{
    private DatabaseConnector databaseConnector;

    public DatabaseInitializer()
    {
        databaseConnector = new DatabaseConnector();
    }

    @PostConstruct
    public void initializeTables()
    {
        Connection connection = null;
        PreparedStatement createTableStatement = null;

        try
        {
            connection = databaseConnector.prepareConnection();
            createTableStatement = connection.prepareStatement(createTableQuery());

            createTableStatement.executeUpdate();
        }
        catch (Exception exception)
        {
            System.out.println("Error something wrong during SQL connection or query execution. Here is some details : \n + " + exception.getMessage());
        }
        finally
        {
            databaseConnector.closeConnection(connection, createTableStatement);
        }
    }

    private String createTableQuery()
    {
        return  "CREATE TABLE PROFILES " +
                "(id INT AUTO_INCREMENT," +
                " name VARCHAR(255)," +
                " age FLOAT, " +
                " height FLOAT, " +
                " weight FLOAT, " +
                " PRIMARY KEY ( id ))";
    }
}
