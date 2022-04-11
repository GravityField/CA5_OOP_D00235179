package DTOs;

import java.time.LocalDate;
import java.util.Objects;

public class Player{

        private int playerID;
        private String firstName;
        private String lastName;
        private double weight;
        private double height;
        private LocalDate birthDate;
        private int championshipWins;

        public Player(int playerID, String firstName, String lastName, double weight, double height, LocalDate birthDate, int championshipWins) {
                this.playerID = playerID;
                this.firstName = firstName;
                this.lastName = lastName;
                this.weight = weight;
                this.height = height;
                this.birthDate = birthDate;
                this.championshipWins = championshipWins;
        }
        public Player(String firstName, String lastName, double weight, double height, LocalDate birthDate, int championshipWins) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.weight = weight;
                this.height = height;
                this.birthDate = birthDate;
                this.championshipWins = championshipWins;
        }

        public int getPlayerID() {
                return playerID;
        }

        public void setPlayerID(int playerID) {
                this.playerID = playerID;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public double getWeight() {
                return weight;
        }

        public void setWeight(double weight) {
                this.weight = weight;
        }

        public double getHeight() {
                return height;
        }

        public void setHeight(double height) {
                this.height = height;
        }

        public LocalDate getBirthDate() {
                return birthDate;
        }

        public void setBirthDate(LocalDate birthDate) {
                this.birthDate = birthDate;
        }

        public int getChampionshipWins() {
                return championshipWins;
        }

        public void setChampionshipWins(int championshipWins) {
                this.championshipWins = championshipWins;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Player player = (Player) o;
                return playerID == player.playerID && Double.compare(player.weight, weight) == 0 && Double.compare(player.height, height) == 0 && championshipWins == player.championshipWins && firstName.equals(player.firstName) && lastName.equals(player.lastName) && birthDate.equals(player.birthDate);
        }

        @Override
        public int hashCode() {
                return Objects.hash(playerID, firstName, lastName, weight, height, birthDate, championshipWins);
        }

        @Override
        public String toString()
        {
                   return String.format("%-10s%-10s%-10s%-10.2f%-10.2f%-15s%5d\n", playerID, firstName, lastName, weight, height, birthDate, championshipWins);
        }


}
