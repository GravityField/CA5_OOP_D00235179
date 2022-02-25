package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println( "Hello World!" );
        ArrayList<Player> playerList = new ArrayList<>();
        initialize(playerList);

    }
    private void initialize(List<Player> list)
    {
        list.add(new Player(0,"Jim","Jam",60.50,160,LocalDate.of(1990,12,10)));
        list.add(new Player(1,"John","Jameson",60.50,160,LocalDate.of(1970,1,17)));
        list.add(new Player(2,"Michael","Flynn",75.50,170,LocalDate.of(2000,9,14)));
        list.add(new Player(3,"Tim","Adejumo",65.50,150,LocalDate.of(2002,4,1)));
        list.add(new Player(3,"Nikita","Fedans",68.90,160,LocalDate.of(2001,5,12)));
    }



}
