package BusinessObjects;

import Comparators.*;
import DAOs.MySqlPlayerDao;
import DAOs.PlayerDaoInterface;
import DTOs.Player;
import DTOs.Team;
import Exceptions.DaoException;

import java.time.LocalDate;
import java.util.*;

/*

Nathan Field
 */
public class App 
{
    public static void main( String[] args )
    {


        System.out.println("\n*** Badminton Team - App ***");
        App app = new App();
        app.start();
    }

    public void start()
    {

        Scanner kb = new Scanner(System.in);

        //Q1
        ArrayList<Player> playerList = new ArrayList<>();
        List<Team> teamsList =  new ArrayList<>();

        initialize(playerList, teamsList);

        //Q3
        Map<String, Player> map = new HashMap<>();

        Map<Integer, Player> map2 = new TreeMap<>();


        // Q6
        PriorityQueue<Player> queue = new PriorityQueue<>(new ChampionshipWinsComparator(SortType.Ascending));
        //Q7
        PriorityQueue<Player> queue2 = new PriorityQueue<>(new TwoFieldComparator(SortType.Ascending));




        TeamNameComparator firstNameComparator = new TeamNameComparator();
        teamsList.sort(firstNameComparator);

        for (Player player : playerList) {
            map.put(player.getLastName(), player);
        }

        for (Player player : playerList) {
            map2.put(player.getPlayerID(), player);
        }

        int option = 0;
        final int DISPLAY_ALL = 1;
        final int FIND_BY_KEY_HASHMAP = 2;
        final int FIND_BY_KEY_TREEMAP = 3;
        final int PRIORITY_QUEUE_SEQUENCE = 4;
        final int PRIORITY_QUEUE_TWO_FIELD = 5;
        final int FIND_ALL_PLAYERS = 6;
        final int FIND_PLAYER_BY_KEY= 7;
        final int DELETE_PLAYER_BY_KEY= 8;
        final int INSERT_PLAYER= 9;
        final int FILTER_PLAYERS= 10;
        final int RETURN_ALL_PLAYERS_JSON = 11;
        final int RETURN_PLAYER_JSON = 12;


        final int EXIT = -1;

        while(option != EXIT)
        {
            printMenu();
            try {
                String usersInput = kb.nextLine();
                option = Integer.parseInt(usersInput);

                switch (option) {
                    case DISPLAY_ALL:
                        displayAllPlayers(playerList);
                        break;
                    case FIND_BY_KEY_HASHMAP:
                        hashMap(map);
                        break;
                    case FIND_BY_KEY_TREEMAP:
                        treeMap(map2);
                        break;
                    case PRIORITY_QUEUE_SEQUENCE:
                        //add 2 third priority
                        queue.add(playerList.get(0));
                        queue.add(playerList.get(1));

                        //add 2 second priority
                        queue.add(playerList.get(2));
                        queue.add(playerList.get(3));
                        //remove and display element
                        queue.remove();
                        System.out.println(queue);

                        queue.add(playerList.get(6));
                        System.out.println(queue);
                        queue.remove();
                        System.out.println(queue);
                        while ( !queue.isEmpty() ) {
                            System.out.println(queue.remove());
                        }
                        break;
                    case PRIORITY_QUEUE_TWO_FIELD:
                        queue2.addAll(playerList);
                        while( !queue2.isEmpty() ) {
                            System.out.println(queue2.remove());
                        }
                        break;
                        //Database onwards
                    case FIND_ALL_PLAYERS:
                            findAllPlayers();
                        break;
                    case FIND_PLAYER_BY_KEY:
                            findPlayerByKey();
                        break;
                    case DELETE_PLAYER_BY_KEY:
                        deletePlayerByID();
                        break;
                    case INSERT_PLAYER:
                        insertPlayer();
                        break;
                    case FILTER_PLAYERS:
                        filterYear();
                        break;
                    case RETURN_ALL_PLAYERS_JSON:
                    returnAllPlayersJSON();
                        break;
                    case RETURN_PLAYER_JSON:
                        returnPlayerJSON();
                        break;
                    case EXIT:
                        break;
                    default:
                        System.out.print("Invalid option - please enter number in range");
                        break;
                }

            }
            catch(InputMismatchException | NumberFormatException e)
            {
                System.out.print("Invalid option - please enter number in range");
            }
        }
        System.out.println("Menu Exited");

    }

    private void printMenu()
    {
        System.out.println("\nMain Menu");
        System.out.println("1.Display All Players");
        System.out.println("2.Retrieve Player By Last Name HashMap");
        System.out.println("3.Retrieve Player By ID TreeMap");
        System.out.println("4. Priority Queue Sequence Simulation");
        System.out.println("5. Priority Queue Two Field");
        System.out.println("Database Section: ");
        System.out.println("6. Find All Players");
        System.out.println("7. Find Player by Key");
        System.out.println("8. Delete Player by Key");
        System.out.println("9. Insert Player");
        System.out.println("10. Filter Players DOB after year");
        System.out.println("11. Return All Players JSON");
        System.out.println("12. Return Player JSON");

        System.out.println("-1. EXIT");
    }
    private void findAllPlayers()
    {
        PlayerDaoInterface IPlayerDao = new MySqlPlayerDao();

        try {
            List<Player> players = IPlayerDao.findAllPlayers();     // call a method in the DAO

            if (players.isEmpty())
                System.out.println("There are no Players");
            else {
                for (Player player : players)
                    System.out.println("Player: " + player.toString());
            }

        }
        catch( DaoException e )
        {
            e.printStackTrace();
        }
    }
    private void returnAllPlayersJSON()
    {
        PlayerDaoInterface IPlayerDao = new MySqlPlayerDao();

        try {
            String playersJSON = IPlayerDao.findAllPlayersJSON();

            if (playersJSON.equals("null"))
                System.out.println("There are no Players");
            else {

                    System.out.println("Player: " + playersJSON);
            }

        }
        catch( DaoException e )
        {
            e.printStackTrace();
        }
    }
    private void returnPlayerJSON()
    {
        PlayerDaoInterface IPlayerDao = new MySqlPlayerDao();
        Scanner kb = new Scanner(System.in);

        try
        {
            System.out.println("Enter ID to search for:");
            int id = kb.nextInt();
            String player = IPlayerDao.findPlayerByIDJSON(id);

            if( !player.equals("null") )
                System.out.println("Player: " + player);
            else {
                System.out.println("There are no Players with this id");
            }
        }
        catch( DaoException e )
        {
            e.printStackTrace();
        }
    }
    private void findPlayerByKey()
    {
        PlayerDaoInterface IPlayerDao = new MySqlPlayerDao();
        Scanner kb = new Scanner(System.in);

        try
        {
            System.out.println("Enter ID to search for:");
            int id = kb.nextInt();
            Player player = IPlayerDao.findPlayerByID(id);

            if( player != null )
                System.out.println("Player: " + player);
            else {
                System.out.println("There are no Players with this id");
            }
        }
        catch( DaoException e )
        {
            e.printStackTrace();
        }

    }
    private void deletePlayerByID()
    {
        PlayerDaoInterface IPlayerDao = new MySqlPlayerDao();
        Scanner kb = new Scanner(System.in);

        try
        {
            System.out.println("Enter ID to delete:");
            int id = kb.nextInt();
            IPlayerDao.deletePlayerByID(id);
        }
        catch( DaoException e )
        {
            e.printStackTrace();
        }

    }
    private void insertPlayer()
    {
        PlayerDaoInterface IPlayerDao = new MySqlPlayerDao();
        Scanner kb = new Scanner(System.in);
        String firstName = "";
        String lastName = "";
        double weight = 0;
        double height = 0;
        int year = 0;
        int month = 0;
        int day = 0;
        LocalDate birthDate = LocalDate.now();
        int championshipWins = 0;

        try
        {
            System.out.println("Enter First Name:");
            firstName = kb.nextLine();
            System.out.println("Enter Last Name:");
            lastName = kb.nextLine();
            System.out.println("Enter Weight:");
            weight = kb.nextDouble();
            System.out.println("Enter Height:");
            height = kb.nextDouble();
            System.out.println("Enter Birth Year:");
            year = kb.nextInt();
            System.out.println("Enter Birth Month:");
            month = kb.nextInt();
            System.out.println("Enter Birth Day:");
            day = kb.nextInt();
            System.out.println("Enter Championship Wins:");
            championshipWins = kb.nextInt();

            birthDate = LocalDate.of(year,month,day);

            IPlayerDao.insertPlayer(firstName,lastName,weight,height,birthDate,championshipWins);
            System.out.println("Player Added");
        }
        catch( DaoException e )
        {
            e.printStackTrace();
        }

    }
    private void filterYear() {
        PlayerDaoInterface IPlayerDao = new MySqlPlayerDao();
        Scanner kb = new Scanner(System.in);
        BirthDateComparator birthDateComparator = new BirthDateComparator(SortType.Ascending);
        int year;
//todo
        System.out.println("Enter Year to filter above");
        year = kb.nextInt();

        try {

            List<Player> players = IPlayerDao.findPlayerUsingFilter(year, birthDateComparator);     // call a method in the DAO

            if (players.isEmpty())
                System.out.println("There are no Players");
            else {
                for (Player player : players)
                    System.out.println("Player: " + player.toString());
            }

        }
        catch( DaoException e )
        {
            e.printStackTrace();
        }

    }


private void initialize(List<Player> list, List<Team> teamsList)
    {

        Player p1 = new Player(0,"Jim","Jam",60.50,160,LocalDate.of(1990,12,10), 0);
        Player p2 = new Player(1,"John","Jameson",60.50,160,LocalDate.of(1970,1,17), 0);
        Player p3 = new Player(2,"Michael","Flynn",75.50,170,LocalDate.of(2000,9,14), 1);
        Player p4 = new Player(5,"Tim","Adejumo",65.50,150,LocalDate.of(2002,4,1), 1);
        Player p5 = new Player(4,"Nikita","Fedans",68.90,160,LocalDate.of(2001,5,12), 2);
        Player p6 = new Player(3,"Jimmy","Jammy",60.50,160,LocalDate.of(1970,12,10), 2);
        Player p7 = new Player(6,"Tim","Jameson",60.50,160,LocalDate.of(1960,1,12), 3);
        Player p8 = new Player(7,"Michelle","Fly",75.50,170,LocalDate.of(2002,9,19), 0);
        Player p9 = new Player(8,"Tim","Ada",65.50,150,LocalDate.of(2006,2,1), 0);
        Player p10 = new Player(9,"Nikki","Federal",68.90,160,LocalDate.of(2005,3,12), 0);

        ArrayList<Player> list1 = new ArrayList<>();
        ArrayList<Player> list2 = new ArrayList<>();
        ArrayList<Player> list3 = new ArrayList<>();

        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);
        list.add(p6);
        list.add(p7);
        list.add(p8);
        list.add(p9);
        list.add(p10);

        list1.add(p1);
        list1.add(p2);
        list1.add(p3);
        list1.add(p4);
        list2.add(p5);
        list2.add(p6);
        list2.add(p7);
        list2.add(p8);
        list3.add(p9);
        list3.add(p10);

        Team t1 = new Team(0,"Team Nebraska", list1);
        Team t2 = new Team(1,"Team Ireland", list2);
        Team t3 = new Team(2,"Team UK", list3);

        teamsList.add(t1);
        teamsList.add(t2);
        teamsList.add(t3);


    }

    private void hashMap(Map<String, Player> map) {
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter Player Last Name to search by:");
        String key = kb.nextLine();

        Player pResult = map.get(key);
        if (pResult != null) {
            System.out.println(pResult);
        } else {
            System.out.println("Not Found");
        }
    }
    private void treeMap(Map<Integer, Player> map2)
    {
        for (Map.Entry<Integer, Player> entry : map2.entrySet()) {
            System.out.println("Value: " + entry.getValue());
        }
    }

    private void displayAllPlayers(List<Player> list)
    {
        System.out.println("\t\t\t\t\t---- Player Table ----");
        System.out.println("PlayerID First Name\tLast Name Weight\tHeight\tDate of Birth");

        for(Player p: list)
        {
            System.out.print(p);
        }
    }

}
