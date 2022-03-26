package DAOs;

import DTOs.Player;
import Exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MySqlPlayerDao extends MySqlDao implements PlayerDaoInterface
{

    @Override
    public List<Player> findAllPlayers() throws DaoException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<Player> playersList = new ArrayList<>();

        try
        {
            connection = this.getConnection();

            String query = "SELECT * FROM PLAYERS";
            ps = connection.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            resultSet = ps.executeQuery();
            while (resultSet.next())
            {

                int playerId = resultSet.getInt("player_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                double weight = resultSet.getDouble("weight");
                double height = resultSet.getDouble("height");
                LocalDate birthDate = resultSet.getDate("dateOfBirth").toLocalDate();
                int championshipWins = resultSet.getInt("championship_wins");

                Player p = new Player(playerId,firstName,lastName,weight,height,birthDate,championshipWins);
                playersList.add(p);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllPlayerResultSet() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllPlayers() " + e.getMessage());
            }
        }
        return playersList;     // may be empty
    }

//    @Override
//    public Player findUserByUsernamePassword(String user_name, String pass_word) throws DaoException
//    {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        User user = null;
//        try
//        {
//            connection = this.getConnection();
//
//            String query = "SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?";
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, user_name);
//            preparedStatement.setString(2, pass_word);
//
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next())
//            {
//                int userId = resultSet.getInt("USER_ID");
//                String username = resultSet.getString("USERNAME");
//                String password = resultSet.getString("PASSWORD");
//                String lastname = resultSet.getString("LAST_NAME");
//                String firesultSettname = resultSet.getString("FIRST_NAME");
//
//                user = new User(userId, firesultSettname, lastname, username, password);
//            }
//        } catch (SQLException e)
//        {
//            throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
//        } finally
//        {
//            try
//            {
//                if (resultSet != null)
//                {
//                    resultSet.close();
//                }
//                if (preparedStatement != null)
//                {
//                    preparedStatement.close();
//                }
//                if (connection != null)
//                {
//                    freeConnection(connection);
//                }
//            } catch (SQLException e)
//            {
//                throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
//            }
//        }
//        return user;     // reference to User object, or null value
//    }

//    @Override
//    public List<Player> findAllPlayersLastNameContains(String subString) throws DaoException {
//        return null;
//    }

//    @Override
//    public List<Player> findAllPlayers() throws DaoException {
//        return null;
//    }

    @Override
    public List<Player> findAllPlayersLastNameContains(String subString) throws DaoException {
        return null;
    }
}

