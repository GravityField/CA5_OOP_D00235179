package DTOs;

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

        public int getMatchID() {
                return matchID;
        }

        public void setMatchID(int matchID) {
                this.matchID = matchID;
        }

        public ArrayList<Team> getTeams() {
                return teams;
        }

        public void setTeams(ArrayList<Team> teams) {
                this.teams = teams;
        }

        public LocalDateTime getDateTime() {
                return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
                this.dateTime = dateTime;
        }
}
