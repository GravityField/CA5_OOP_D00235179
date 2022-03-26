package Comparators;

import DTOs.Player;

import java.util.Comparator;

public class TwoFieldComparator implements Comparator<Player> {

    private SortType sortType;

    public TwoFieldComparator(SortType sortType)
    {
        this.sortType = sortType;
    }
    @Override
    public int compare(Player p1, Player p2) {
        int direction = sortType.getValue();


        boolean sameName = p1.getFirstName().equalsIgnoreCase(p2.getFirstName());

        if(sameName) {
            return direction * (p1.getChampionshipWins() - p2.getChampionshipWins());
        }
else
{
    return  direction * (p1.getFirstName().compareTo(p2.getFirstName()));
}


    }


}
