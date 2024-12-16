package fr.pantheonsorbonne.miage.game;

import java.io.IOException;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }
    

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public int getScore() {
        return score;
    }

    // Method to check if the player has no cards left
    public boolean hasNoCards() {
        return hand == null || hand.isEmpty();
    }

    // Method to notify winner (only applicable for network players)
    public void notifyWinner(Player winner) throws IOException {
        // Implement logic to notify the player about the winner (could be network-based)
        System.out.println(this.name + " is notified that " + winner.getName() + " won.");
    }

    @Override
    public String toString() {
        return name + " (Score: " + score + ")";
    }

    public String getName() {
        return name;
    }
}
