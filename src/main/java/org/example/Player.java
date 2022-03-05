package org.example;

import java.time.LocalDate;

public class Player {

        private int playerID;
        private String firstName;
        private String lastName;
        private double weight;
        private double height;
        private LocalDate birthDate;

        public Player(int playerID, String firstName, String lastName, double weight, double height, LocalDate birthDate) {
                this.playerID = playerID;
                this.firstName = firstName;
                this.lastName = lastName;
                this.weight = weight;
                this.height = height;
                this.birthDate = birthDate;
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



        @Override
        public String toString() {
                return "Player{" +
                        "playerID=" + playerID +
                        ", firstName='" + firstName + '\'' +
                        ", lastName='" + lastName + '\'' +
                        ", weight=" + weight +
                        ", height=" + height +
                        ", birthDate=" + birthDate +
                        '}';
        }
}
