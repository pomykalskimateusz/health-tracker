package health.tracker.repository.plan;

import health.tracker.repository.DatabaseConnector;
import health.tracker.repository.product.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanRepository
{
    public static final String TABLE_NAME = "PLANS";

    private DatabaseConnector databaseConnector;

    public PlanRepository()
    {
        databaseConnector = new DatabaseConnector();
    }

    /**
     * @param plan - Plan param, model which is saved
     * @return boolean - true or false, depends on failure during execute connection to database
     */
    public boolean save(Plan plan)
    {
        Connection connection = null;
        PreparedStatement statement = null;

        try
        {
            connection = databaseConnector.prepareConnection();

            statement = connection.prepareStatement(insertQuery(plan));

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
     * @param day - String param, which determine specific day
     * @return List - list of products for specific day for hardcoded user.
     */
    public List<Product> findProductsFor(String day)
    {
        Connection connection = null;
        Statement statement = null;
        ArrayList<Product> products = new ArrayList<>();

        try
        {
            connection = databaseConnector.prepareConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(productsForQuery(day));

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
     * @param productId - Long param, which determine product by id
     * @param day - String param, which determine from which day list should remove product
     * @return boolean - true or false, depends on failure during execute connection to database
     */
    public boolean delete(Long productId, String day)
    {
        Connection connection = null;
        PreparedStatement statement = null;

        try
        {
            connection = databaseConnector.prepareConnection();

            statement = connection.prepareStatement(deleteByIdQuery(productId, day));

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

    private String insertQuery(Plan plan)
    {
        return "INSERT INTO " + TABLE_NAME + "(day, productId) VALUES ('" + plan.getDay() + "', " +  plan.getProductId() + ");";
    }

    private String productsForQuery(String day)
    {
        return "SELECT PRODUCTS.id, PRODUCTS.name, PRODUCTS.calorific " +
                " FROM PLANS " +
                " INNER JOIN PRODUCTS " +
                " ON PLANS.productId=PRODUCTS.id WHERE PLANS.day='"+ day +"';";
    }

    private String deleteByIdQuery(Long id, String day)
    {
        return "DELETE FROM " + TABLE_NAME + " WHERE productId=" + id + " AND day='"+day+"';";
    }
}
