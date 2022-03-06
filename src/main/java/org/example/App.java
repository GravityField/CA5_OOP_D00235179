package org.example;

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
        ArrayList<Player> playerList = new ArrayList<>();
        List<Team> teamsList =  new ArrayList<>();

        initialize(playerList, teamsList);

        Map<Player, Team> map = new HashMap<>();
        Map<String, Player> map2 = new TreeMap<>();
        TeamNameComparator firstNameComparator = new TeamNameComparator();
        teamsList.sort(firstNameComparator);

        map.put(playerList.get(0), teamsList.get(0));
        map.put(playerList.get(1), teamsList.get(0));
        map.put(playerList.get(2), teamsList.get(0));
        map.put(playerList.get(3), teamsList.get(0));
        map.put(playerList.get(4), teamsList.get(1));
        map.put(playerList.get(5), teamsList.get(1));
        map.put(playerList.get(6), teamsList.get(1));
        map.put(playerList.get(7), teamsList.get(1));
        map.put(playerList.get(8), teamsList.get(2));
        map.put(playerList.get(9), teamsList.get(2));


        map2.put(playerList.get(0).getFirstName(), playerList.get(0));
        map2.put(playerList.get(1).getFirstName(), playerList.get(1));
        map2.put(playerList.get(2).getFirstName(), playerList.get(2));
        map2.put(playerList.get(3).getFirstName(), playerList.get(3));
        map2.put(playerList.get(4).getFirstName(), playerList.get(4));
        map2.put(playerList.get(5).getFirstName(), playerList.get(5));
        map2.put(playerList.get(6).getFirstName(), playerList.get(6));
        map2.put(playerList.get(7).getFirstName(), playerList.get(7));
        map2.put(playerList.get(8).getFirstName(), playerList.get(8));
        map2.put(playerList.get(9).getFirstName(), playerList.get(9));

        //        String key = "Jim";
        //        String team = map.get(key);
        //        System.out.println(key + "'s team is: " +  team);


        int option = 0;
        int idKey;
        final int DISPLAY_ALL = 1;
        final int FIND_BY_KEY = 2;
        final int DISPLAY_ALL_TREEMAP = 3;
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
                    case FIND_BY_KEY:
                        System.out.println("Enter Player ID to search by:");
                        idKey = kb.nextInt();
                        Player pResult = playerList.get(idKey);
                        Team tResult = retrieveByKeyHash(map, pResult);
                        System.out.println(tResult);
                        if (tResult != null) {
                            System.out.println(tResult);
                        } else {
                            System.out.println("Not Found");
                        }
                        break;
                    case DISPLAY_ALL_TREEMAP:
                        displayAllTreeMap(map2);
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
        System.out.println("2.Retrieve Player By Key HashMap");
        System.out.println("3.Retrieve Team By Key TreeMap");
        System.out.println("4.");
        System.out.println("-1. EXIT");
    }
    private void initialize(List<Player> list, List<Team> teamsList)
    {
        Player p1 = new Player(0,"Jim","Jam",60.50,160,LocalDate.of(1990,12,10));
        Player p2 = new Player(1,"John","Jameson",60.50,160,LocalDate.of(1970,1,17));
        Player p3 = new Player(2,"Michael","Flynn",75.50,170,LocalDate.of(2000,9,14));
        Player p4 = new Player(3,"Tim","Adejumo",65.50,150,LocalDate.of(2002,4,1));
        Player p5 = new Player(4,"Nikita","Fedans",68.90,160,LocalDate.of(2001,5,12));
        Player p6 = new Player(5,"Jimmy","Jammy",60.50,160,LocalDate.of(1970,12,10));
        Player p7 = new Player(6,"Johnny","Jameson",60.50,160,LocalDate.of(1960,1,12));
        Player p8 = new Player(7,"Michelle","Fly",75.50,170,LocalDate.of(2002,9,19));
        Player p9 = new Player(8,"Timmy","Ada",65.50,150,LocalDate.of(2006,2,1));
        Player p10 = new Player(9,"Nikki","Federal",68.90,160,LocalDate.of(2005,3,12));

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
    private void displayAllTreeMap(Map<String, Player> map2)
    {
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter Key to search by:");
        String key = kb.nextLine();
        Player pResult = retrieveByKeyTree(map2, key);
        if (pResult != null) {
            System.out.println(pResult);
        } else {
            System.out.println("Not Found");
        }
    }
    private void displayAllPlayers(List<Player> list)
    {
        System.out.println("\t\t\t\t\t---- Player Table ----");
        System.out.println("PlayerID First Name\tLast Name Weight\tHeight\tDate of Birth");

        for(Player p: list)
        {
            System.out.printf("%-10s%-10s%-10s%-10.2f%-10.2f%-15s\n", p.getPlayerID(), p.getFirstName(), p.getLastName(), p.getWeight(), p.getHeight(), p.getBirthDate());
        }
    }
    private Team retrieveByKeyHash(Map<Player, Team> map, Player key)
    {
        return map.get(key);
    }
    private Player retrieveByKeyTree(Map<String, Player> map, String key)
    {
        return map.get(key);
    }

}
