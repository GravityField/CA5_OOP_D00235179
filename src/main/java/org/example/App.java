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
        initialize(playerList);
        Map<String, String> map = new HashMap<>();

        map.put(playerList.get(0).getFirstName(), "Some Team Name" );
        map.put(playerList.get(1).getFirstName(), "Some Team Name" );
        map.put(playerList.get(3).getFirstName(), "Different Team Name" );
        map.put(playerList.get(4).getFirstName(), "Some Team Name" );
        map.put(playerList.get(5).getFirstName(), "Some Team Name" );
        map.put(playerList.get(6).getFirstName(), "Different Team Name" );
        map.put(playerList.get(7).getFirstName(), "Some Team Name" );
        map.put(playerList.get(8).getFirstName(), "Some Team Name" );
        map.put(playerList.get(9).getFirstName(), "Different Team Name" );

//        String key = "Jim";
//        String team = map.get(key);
//        System.out.println(key + "'s team is: " +  team);



        int option = 0;
        final int DISPLAY_ALL = 1;
        final int FIND_BY_KEY = 2;
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
                        System.out.println("Enter Key to search by:");
                        String key = kb.nextLine();
                        String result = retrieveByKey(map, key);
                        if (result != null) {
                            System.out.println(result);
                        } else {
                            System.out.println("Not Found");
                        }
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
        System.out.println("Main Menu");
        System.out.println("1.Display All Players");
        System.out.println("2.Retrieve Player By Key");
        System.out.println("3.");
        System.out.println("4.");
        System.out.println("0. EXIT");
    }
    private void initialize(List<Player> list)
    {
        list.add(new Player(0,"Jim","Jam",60.50,160,LocalDate.of(1990,12,10)));
        list.add(new Player(1,"John","Jameson",60.50,160,LocalDate.of(1970,1,17)));
        list.add(new Player(2,"Michael","Flynn",75.50,170,LocalDate.of(2000,9,14)));
        list.add(new Player(3,"Tim","Adejumo",65.50,150,LocalDate.of(2002,4,1)));
        list.add(new Player(4,"Nikita","Fedans",68.90,160,LocalDate.of(2001,5,12)));
        list.add(new Player(5,"Jimmy","Jammy",60.50,160,LocalDate.of(1970,12,10)));
        list.add(new Player(6,"Johnny","Jameson",60.50,160,LocalDate.of(1960,1,12)));
        list.add(new Player(7,"Michelle","Fly",75.50,170,LocalDate.of(2002,9,19)));
        list.add(new Player(8,"Timmy","Ada",65.50,150,LocalDate.of(2006,2,1)));
        list.add(new Player(9,"Nikki","Federal",68.90,160,LocalDate.of(2005,3,12)));
    }
    private void displayAllPlayers(List<Player> list)
    {
        for(Player p: list)
        {
            System.out.println( "Player ID: " + p.getPlayerID() + " First Name: " + p.getFirstName() + " Last Name: "+ p.getLastName() +
                    " Weight: " + p.getWeight() + " Height: " +p.getHeight() + " BirthDate: " + p.getBirthDate() );
        }
    }
    private String retrieveByKey(Map<String, String> map, String key)
    {

        return map.get(key);
    }

}
