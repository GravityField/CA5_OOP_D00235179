package DAOs;

import Comparators.BirthDateComparator;
import Comparators.ChampionshipWinsComparator;
import DTOs.Player;
import Exceptions.DaoException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import DAOs.LocalDateAdapter;
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
                throw new DaoException("deletePlayerByID() " + e.getMessage());
            }
        }
    }
    @Override
    public void insertPlayer(String firstName, String lastName, double weight, double height, LocalDate birthDate, int championshipWins) throws DaoException
    {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = this.getConnection();

            String query = "INSERT INTO PLAYERS VALUES (null,?,?,?,?,?,?)";


            ps = connection.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setDouble(3, weight);
            ps.setDouble(4, height);
            ps.setDate(5, Date.valueOf(birthDate));
            ps.setInt(6, championshipWins);

            //test for an existing user would be nice
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new DaoException("insertPlayer() " + e.getMessage());
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("insertPlayer() " + e.getMessage());
            }
        }

    }
    @Override
    public void insertPlayer(int id,String firstName, String lastName, double weight, double height, LocalDate birthDate, int championshipWins) throws DaoException
    {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = this.getConnection();

            String query = "INSERT INTO PLAYERS VALUES (?,?,?,?,?,?,?)";


            ps = connection.prepareStatement(query);
            ps.setInt(1,id);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setDouble(4, weight);
            ps.setDouble(5, height);
            ps.setDate(6, Date.valueOf(birthDate));
            ps.setInt(7, championshipWins);

            //test for an existing user would be nice
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new DaoException("insertPlayer() " + e.getMessage());
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("insertPlayer() " + e.getMessage());
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
    public List<Player> findPlayerUsingFilter(int year, BirthDateComparator birthDateComparator) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Player player = null;
        List<Player> playersList = new ArrayList<>();
        try
        {
            connection = this.getConnection();

            String query = "SELECT * FROM PLAYERS WHERE Year(dateOfBirth) > ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, year);

            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                int playerId = resultSet.getInt("player_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                double weight = resultSet.getDouble("weight");
                double height = resultSet.getDouble("height");
                LocalDate birthDate = resultSet.getDate("dateOfBirth").toLocalDate();
                int championshipWins = resultSet.getInt("championship_wins");

                player = new Player(playerId,firstName,lastName,weight,height,birthDate,championshipWins);
                playersList.add(player);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findPlayerUsingFilter() " + e.getMessage());
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
                throw new DaoException("findPlayerUsingFilter() " + e.getMessage());
            }
        }
        playersList.sort(birthDateComparator);
        return playersList;
    }
    @Override
    public String findAllPlayersJSON()
    {
        List<Player> playersList;
        Gson gsonParser = new GsonBuilder()
                //.setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
//        ObjectMapper mapper = new ObjectMapper();

        String playerJSON = "";
        try
        {
            playersList = findAllPlayers();
            // playerJSON = gsonParser.toJson(playersList);
            playerJSON =  gsonParser.toJson(playersList);
        } catch (DaoException throwable) {
            throwable.printStackTrace();
        }
        return playerJSON;
    }
    @Override
    public String findPlayerByIDJSON(int id) throws DaoException {

        Player player;
            String playerJSON = "";
        try
        {
        Gson gsonParser = new GsonBuilder()
                //.setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
//        ObjectMapper mapper = new ObjectMapper();
//        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        mapper.setDateFormat(df);

            player = findPlayerByID(id);
             playerJSON = gsonParser.toJson(player);
//            playerJSON =  mapper.writeValueAsString(player);
        } catch (DaoException e)
        {
            System.out.println(e);
        }
        return playerJSON;
    }
}

