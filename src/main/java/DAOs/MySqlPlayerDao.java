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

    @Override
    public void deletePlayerByID(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement ps = null;
        int result = 0;

        try {
            connection = this.getConnection();

            String query = "DELETE FROM PLAYERS WHERE player_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);

            result = ps.executeUpdate();
            if(result != 0)
            {
                System.out.println("Record deleted");
            }
            else{
                System.out.println("Record does not exist");
            }

        } catch (SQLException e) {
            throw new DaoException("deletePlayerByID() " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("findAllPlayers() " + e.getMessage());
            }
        }
    }
    @Override
    public Player findPlayerByID(int id) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Player player = null;
        try
        {
            connection = this.getConnection();

            String query = "SELECT * FROM PLAYERS WHERE player_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                int playerId = resultSet.getInt("player_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                double weight = resultSet.getDouble("weight");
                double height = resultSet.getDouble("height");
                LocalDate birthDate = resultSet.getDate("dateOfBirth").toLocalDate();
                int championshipWins = resultSet.getInt("championship_wins");

                player = new Player(playerId,firstName,lastName,weight,height,birthDate,championshipWins);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findPlayerById() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findPlayerById() " + e.getMessage());
            }
        }
        return player;
    }

    @Override
    public List<Player> findAllPlayersLastNameContains(String subString) throws DaoException {
        return null;
    }
}

