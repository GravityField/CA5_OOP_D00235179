package org.example;

import java.time.LocalDate;
import java.util.List;

public class Team {

    private int teamID;
    private String teamName;
    private List<Player> members;

    public Team(int teamID, String teamName, List<Player> members) {
        this.teamID = teamID;
        this.teamName = teamName;
        this.members = members;
    }
}
