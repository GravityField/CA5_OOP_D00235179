package DAOs;

/** OOP Feb 2022
 * UserDaoInterface
 *
 * Declares the methods that all UserDAO types must implement,
 * be they MySql User DAOs or Oracle User DAOs etc...
 *
 * Classes from the Business Layer (users of this DAO interface)
 * should use reference variables of this interface type to avoid
 * dependencies on the underlying concrete classes (e.g. MySqlUserDao).
 *
 * More sophisticated implementations will use a factory
 * method to instantiate the appropriate DAO concrete classes
 * by reading database configuration information from a
 * configuration file (that can be changed without altering source code)
 *
 * Interfaces are also useful when testing, as concrete classes
 * can be replaced by mock DAO objects.
 */

import Comparators.BirthDateComparator;
import Comparators.ChampionshipWinsComparator;
import DTOs.Player;
import Exceptions.DaoException;

import java.time.LocalDate;
import java.util.List;

public interface PlayerDaoInterface
{
    //7
    public List<Player> findAllPlayers() throws DaoException;
    //8
    public Player findPlayerByID(int id) throws DaoException;
    //9
    public void deletePlayerByID(int id) throws DaoException;
    //10
    public void insertPlayer(String firstName, String lastName, double weight, double height, LocalDate birthDate, int championshipWins) throws DaoException;
    public void insertPlayer(int id,String firstName, String lastName, double weight, double height, LocalDate birthDate, int championshipWins) throws DaoException;
    //11
    public List<Player> findPlayerUsingFilter(int year, BirthDateComparator birthDateComparator) throws DaoException;
    //12
    public String findAllPlayersJSON() throws DaoException;
    //13
    public String findPlayerByIDJSON(int id) throws DaoException;



}

