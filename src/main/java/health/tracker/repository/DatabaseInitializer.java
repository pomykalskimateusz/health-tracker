package health.tracker.repository;

import health.tracker.repository.plan.PlanRepository;
import health.tracker.repository.product.ProductRepository;
import health.tracker.repository.profile.User;
import health.tracker.repository.profile.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

@Component
public class DatabaseInitializer
{
    private DatabaseConnector databaseConnector;
    private UserRepository userRepository;

    public DatabaseInitializer()
    {
        userRepository = new UserRepository();
        databaseConnector = new DatabaseConnector();
    }

    @PostConstruct
    public void initializeTables()
    {
        Connection connection = null;
        PreparedStatement userTableStatement = null;
        PreparedStatement productTableStatement = null;
        PreparedStatement planTableStatement = null;

        try
        {
            connection = databaseConnector.prepareConnection();

            userTableStatement = connection.prepareStatement(createUserTable());
            productTableStatement = connection.prepareStatement(createProductTable());
            planTableStatement = connection.prepareStatement(createPlanTable());

            productTableStatement.executeUpdate();
            planTableStatement.executeUpdate();
            userTableStatement.executeUpdate();
        }
        catch (Exception exception)
        {
            System.out.println("Error something wrong during SQL connection or query execution. Here is some details : \n + " + exception.getMessage());
        }
        finally
        {
            databaseConnector.closeConnection(connection, Arrays.asList(userTableStatement, productTableStatement, planTableStatement));
        }

        userRepository.save(emptyUser());
    }

    private String createUserTable()
    {
        return  "CREATE TABLE " + UserRepository.TABLE_NAME +
                "(id LONG AUTO_INCREMENT," +
                " name VARCHAR(255)," +
                " age FLOAT, " +
                " height FLOAT, " +
                " weight FLOAT, " +
                " isFemale BOOLEAN, " +
                " PRIMARY KEY ( id ))";
    }

    private String createProductTable()
    {
        return  "CREATE TABLE " + ProductRepository.TABLE_NAME +
                "(id LONG AUTO_INCREMENT," +
                " name VARCHAR(255)," +
                " calorific FLOAT, " +
                " PRIMARY KEY ( id ))";
    }

    private String createPlanTable()
    {
        return "CREATE TABLE " + PlanRepository.TABLE_NAME +
                " (id LONG AUTO_INCREMENT, " +
                " day VARCHAR(255), " +
                " productId LONG, " +
                " PRIMARY KEY ( id ), " +
                " FOREIGN KEY (productId) REFERENCES PRODUCTS(id));";
    }

    private User emptyUser()
    {
        return User
                .builder()
                .name("")
                .weight(0.0)
                .height(0.0)
                .age(0.0)
                .isFemale(true)
                .build();
    }
}
