package health.tracker.repository;

import health.tracker.repository.product.Product;
import health.tracker.repository.product.ProductRepository;
import health.tracker.repository.profile.User;
import health.tracker.repository.profile.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseInitializer
{
    private DatabaseConnector databaseConnector;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public DatabaseInitializer()
    {
        productRepository = new ProductRepository();
        userRepository = new UserRepository();
        databaseConnector = new DatabaseConnector();
    }

    @PostConstruct
    public void initializeTables()
    {
        Connection connection = null;
        PreparedStatement userTableStatement = null;
        PreparedStatement productTableStatement = null;

        try
        {
            connection = databaseConnector.prepareConnection();

            userTableStatement = connection.prepareStatement(createUserTable());
            productTableStatement = connection.prepareStatement(createProductTable());

            productTableStatement.executeUpdate();
            userTableStatement.executeUpdate();
        }
        catch (Exception exception)
        {
            System.out.println("Error something wrong during SQL connection or query execution. Here is some details : \n + " + exception.getMessage());
        }
        finally
        {
            databaseConnector.closeConnection(connection, Arrays.asList(userTableStatement, productTableStatement));
        }

        userRepository.save(emptyUser());
        prepareProductList().forEach(productRepository::save);
    }

    private String createUserTable()
    {
        return  "CREATE TABLE " + UserRepository.TABLE_NAME +
                "(id LONG AUTO_INCREMENT," +
                " name VARCHAR(255)," +
                " age FLOAT, " +
                " height FLOAT, " +
                " weight FLOAT, " +
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

    private List<Product> prepareProductList()
    {
        return Arrays.asList
                (
                    prepareProduct("Jablko"),
                    prepareProduct("Szynka"),
                    prepareProduct("Kurczak"),
                    prepareProduct("Bułka"),
                    prepareProduct("Jajko"),
                    prepareProduct("Ser")
                );
    }

    private Product prepareProduct(String name)
    {
        return Product
                .builder()
                .name(name)
                .calorific(Math.random())
                .build();
    }

    private User emptyUser()
    {
        return User
                .builder()
                .name("")
                .weight(0.0)
                .height(0.0)
                .age(0.0)
                .build();
    }
}
