package DAOs;

import BusinessObjects.App;
import DTOs.Player;
import Exceptions.DaoException;
import junit.framework.TestCase;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MySqlPlayerDaoTest extends TestCase {

    public void testFindAllPlayers() {


    }




    public void testFindPlayerByID() throws DaoException {

            System.out.println("Test for Feature 8 - FindPlayerByID");

            int id = 5;
            Player expectedPlayer = new Player(5, "Nikita","Fedans",68.9,160,LocalDate.of(2001,5,12),2);
            MySqlPlayerDao mySqlPlayerDao = new MySqlPlayerDao();
            assertEquals(expectedPlayer, mySqlPlayerDao.findPlayerByID(id));


    }
    public void testDeletePlayerByID() throws DaoException {
        System.out.println("Test for Feature 9 - deletePlayer");
        MySqlPlayerDao mySqlPlayerDao = new MySqlPlayerDao();
        mySqlPlayerDao.deletePlayerByID(999);
        Player newPlayer = new Player(999,"Nikita","Fedans",68.9,160,LocalDate.of(2001,5,12),2);
        mySqlPlayerDao.insertPlayer(newPlayer.getPlayerID(),newPlayer.getFirstName(), newPlayer.getLastName(), newPlayer.getWeight(), newPlayer.getHeight(), newPlayer.getBirthDate(), newPlayer.getChampionshipWins());
        mySqlPlayerDao.deletePlayerByID(999);

        assertNull(mySqlPlayerDao.findPlayerByID(999));
    }

    public void testInsertPlayer() throws DaoException {
        System.out.println("Test for Feature 10 - insertPlayer");

//        Player newPlayer = new Player(5, "Nikita","Fedans",68.9,160,LocalDate.of(2001,5,12),2);
        MySqlPlayerDao mySqlPlayerDao = new MySqlPlayerDao();
        mySqlPlayerDao.deletePlayerByID(999);
        Player newPlayer = new Player(999,"Nikita","Fedans",68.9,160,LocalDate.of(2001,5,12),2);
        mySqlPlayerDao.insertPlayer(newPlayer.getPlayerID(),newPlayer.getFirstName(), newPlayer.getLastName(), newPlayer.getWeight(), newPlayer.getHeight(), newPlayer.getBirthDate(), newPlayer.getChampionshipWins());
        assertEquals( newPlayer,mySqlPlayerDao.findPlayerByID(999));
    }
    public void testFindPlayerUsingFilter() {
    }

    public void testFindAllPlayersJSON() {
    }

    public void testFindPlayerByIDJSON() {
    }
}