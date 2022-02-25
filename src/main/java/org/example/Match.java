package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Match {

        private int matchID;
        private ArrayList<Player> players = new ArrayList<>();
        private LocalDateTime dateTime;

        public Match(int matchID, ArrayList<Player> players, LocalDateTime dateTime) {
                this.matchID = matchID;
                this.players = players;
                this.dateTime = dateTime;
        }
}
