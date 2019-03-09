package health.tracker.repository.profile;

import health.tracker.repository.DatabaseConnector;
import health.tracker.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProfileRepository implements Repository<ProfileModel>
{
    private static final String TABLE_NAME = "profile";

    private DatabaseConnector databaseConnector;

    public ProfileRepository()
    {
        databaseConnector = new DatabaseConnector();
    }

    @Override
    public boolean save(ProfileModel profileModel)
    {
        Connection connection = null;
        PreparedStatement statement = null;

        try
        {
            connection = databaseConnector.prepareConnection();
            System.out.println("SUCCESSFULY PREPARE CONNECTION");

            statement = connection.prepareStatement(insertQuery());

            statement.setString(1, profileModel.getName());
            statement.setDouble(2, profileModel.getAge());
            statement.setDouble(3, profileModel.getHeight());
            statement.setDouble(4, profileModel.getWeight());
            System.out.println("SUCCESSFULY SET PARAMETERS");
            statement.executeUpdate();


            System.out.println("SUCCESSFULLY SAVE PROFILE - " + profileModel.getName());
            return true;
        }
        catch (Exception sqlException)
        {
            System.out.println("Error something wrong during SQL connection or query execution.");

            return false;
        }
        finally
        {
           databaseConnector.closeConnection(connection, statement);
        }
    }

    private String insertQuery()
    {
        return "INSERT INTO " + TABLE_NAME + "(NAME, AGE, HEIGHT, WEIGHT) VALUES ? ? ? ?";
    }
}
