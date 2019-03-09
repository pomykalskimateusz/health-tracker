package health.tracker.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Service
public class DatabaseInitializer
{
    @Autowired
    private DatabaseConnector databaseConnector;

    @PostConstruct
    public void initializeTables()
    {
        System.out.println("Initialization of database.");

        Connection connection = null;
        PreparedStatement createDatabaseStatement = null;
        PreparedStatement createTableStatement = null;

        try
        {
            connection = databaseConnector.prepareConnection();
            createDatabaseStatement = connection.prepareStatement("CREATE DATABASE healthtracker");
            createTableStatement = connection.prepareStatement("CREATE TABLE profile(NAME VARCHAR(255), AGE FLOAT, HEIGHT FLOAT, WEIGHT FLOAT, PRIMARY KEY (NAME))");

            createDatabaseStatement.executeUpdate();
            createTableStatement.executeUpdate();

        }
        catch (Exception sqlException)
        {
            System.out.println("Error something wrong during SQL connection or query execution.");
        }
        finally
        {
            databaseConnector.closeConnection(connection, createDatabaseStatement);
            databaseConnector.closeConnection(connection, createTableStatement);
        }
    }
}
