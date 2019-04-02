package health.tracker.repository.product;

import health.tracker.repository.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductRepository
{
    public static final String TABLE_NAME = "PRODUCTS";

    private DatabaseConnector databaseConnector;

    public ProductRepository()
    {
        databaseConnector = new DatabaseConnector();
    }

    /**
     * @param product - Product param, model which is saved
     * @return boolean - true or false, depends on failure during execute connection to database
     */
    public boolean save(Product product)
    {
        Connection connection = null;
        PreparedStatement statement = null;

        try
        {
            connection = databaseConnector.prepareConnection();

            statement = connection.prepareStatement(insertQuery());

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getCalorific());

            statement.executeUpdate();

            return true;
        }
        catch (Exception exception)
        {
            System.out.println("Error something wrong during SQL connection or query execution. Here is some details : \n" + exception.getMessage());
            return false;
        }
        finally
        {
            databaseConnector.closeConnection(connection, Collections.singletonList(statement));
        }
    }

    /**
     * @return List - list of all products available in database
     */
    public List<Product> findAll()
    {
        Connection connection = null;
        Statement statement = null;
        ArrayList<Product> products = new ArrayList<>();

        try
        {
            connection = databaseConnector.prepareConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(selectQuery());

            while(resultSet.next())
            {
                products.add(Product
                        .builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .calorific(resultSet.getDouble("calorific"))
                        .build());
            }

            try
            {
                resultSet.close();
            }
            catch (SQLException ignored) {}

            return products;
        }
        catch (Exception exception)
        {
            System.out.println("Error something wrong during SQL connection or query execution. Here is some details : \n" + exception.getMessage());
            return Collections.emptyList();
        }
        finally
        {
            databaseConnector.closeConnection(connection, Collections.singletonList(statement));
        }
    }

    /**
     * @param product - Product param, which determine product to delete
     * @return boolean - true or false, depends on failure during execute connection to database
     */
    public boolean delete(Product product)
    {
        Connection connection = null;
        PreparedStatement statement = null;

        try
        {
            connection = databaseConnector.prepareConnection();

            statement = connection.prepareStatement(deleteByIdQuery(product.getId()));

            statement.executeUpdate();

            return true;
        }
        catch (Exception exception)
        {
            System.out.println("Error something wrong during SQL connection or query execution. Here is some details : \n" + exception.getMessage());
            return false;
        }
        finally
        {
            databaseConnector.closeConnection(connection, Collections.singletonList(statement));
        }
    }

    private String insertQuery()
    {
        return "INSERT INTO " + TABLE_NAME + "(NAME, CALORIFIC) VALUES (?,?);";
    }

    private String selectQuery()
    {
        return "SELECT * FROM " + TABLE_NAME + ";";
    }

    private String deleteByIdQuery(Long id)
    {
        return "DELETE FROM " + TABLE_NAME + " WHERE id = " + id + ";";
    }
}
