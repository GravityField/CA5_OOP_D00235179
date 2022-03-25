package org.example;

import DTOs.Player;

import java.util.Comparator;

public class ChampionshipWinsComparator implements Comparator<Player> {

    private SortType sortType;

    public ChampionshipWinsComparator(SortType sortType)
    {
        this.sortType = sortType;
    }
    @Override
    public int compare(Player p1, Player p2) {
        int direction = sortType.getValue();
        return direction * (p1.getChampionshipWins() - p2.getChampionshipWins());
    }


}
