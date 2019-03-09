package health.tracker.repository;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
