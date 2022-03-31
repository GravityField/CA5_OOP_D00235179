package Comparators;

import DTOs.Player;

import java.util.Comparator;

public class BirthDateComparator implements Comparator<Player> {

    private SortType sortType;

    public BirthDateComparator(SortType sortType)
    {
        this.sortType = sortType;
    }
    @Override
    public int compare(Player p1, Player p2) {
        int direction = sortType.getValue();
        return direction * (p1.getBirthDate().compareTo(p2.getBirthDate()));
    }


}
