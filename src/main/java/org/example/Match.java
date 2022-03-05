package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Match {

        private int matchID;
        private ArrayList<Team> teams = new ArrayList<>();
        private LocalDateTime dateTime;

        public Match(int matchID, ArrayList<Team> teams, LocalDateTime dateTime) {
                this.matchID = matchID;
                this.teams = teams;
                this.dateTime = dateTime;
        }
}
