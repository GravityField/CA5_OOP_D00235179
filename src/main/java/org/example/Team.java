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

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Player> getMembers() {
        return members;
    }

    public void setMembers(List<Player> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamID=" + teamID +
                ", teamName='" + teamName + '\'' +
                ", members=" + members +
                '}';
    }
}


