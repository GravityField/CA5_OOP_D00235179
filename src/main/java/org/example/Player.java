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
}
