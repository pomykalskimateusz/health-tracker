package health.tracker.repository.profile;

import health.tracker.repository.DatabaseConnector;

import java.sql.*;
import java.util.Collections;

public class UserRepository
{
    public static final String TABLE_NAME = "USERS";

    private DatabaseConnector databaseConnector;

    public UserRepository()
    {
        databaseConnector = new DatabaseConnector();
    }

    public boolean save(User user)
    {
        Connection connection = null;
        PreparedStatement statement = null;

        try
        {
            connection = databaseConnector.prepareConnection();

            statement = connection.prepareStatement(insertQuery());

            statement.setString(1, user.getName());
            statement.setDouble(2, user.getAge());
            statement.setDouble(3, user.getHeight());
            statement.setDouble(4, user.getWeight());
            statement.setBoolean(5, user.isFemale());

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

    public User findById(Long id)
    {
        Connection connection = null;
        Statement statement = null;
        User user = null;

        try
        {
            connection = databaseConnector.prepareConnection();

            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(getByIdQuery(id));

            while(resultSet.next())
            {
                user = User
                        .builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .age(resultSet.getDouble("age"))
                        .height(resultSet.getDouble("height"))
                        .weight(resultSet.getDouble("weight"))
                        .isFemale(resultSet.getBoolean("isFemale"))
                        .build();
            }

            try
            {
                resultSet.close();
            }
            catch (SQLException ignored) {}

            return user;
        }
        catch (Exception exception)
        {
            System.out.println("Error something wrong during SQL connection or query execution. Here is some details : \n" + exception.getMessage());
            return User.empty();
        }
        finally
        {
            databaseConnector.closeConnection(connection, Collections.singletonList(statement));
        }
    }

    public boolean updateById(Long id, User user)
    {
        Connection connection = null;
        Statement statement = null;

        try
        {
            connection = databaseConnector.prepareConnection();

            statement = connection.createStatement();

            statement.executeUpdate(updateByIdQuery(id, user));

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
        return "INSERT INTO " + TABLE_NAME + "(NAME, AGE, HEIGHT, WEIGHT, isFemale) VALUES (?,?,?,?,?);";
    }

    private String getByIdQuery(Long id)
    {
        return "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id + ";";
    }

    private String updateByIdQuery(Long id, User user)
    {
        return "UPDATE " + TABLE_NAME + " SET" +
                " name = '" + user.getName() + "'"+
                ", age = '" + user.getAge() + "'" +
                ", height = '" + user.getHeight() + "'" +
                ", weight = '" + user.getWeight() + "'" +
                ", isFemale = " + user.isFemale()  +
                " WHERE id = " + id + ";";
    }
}
