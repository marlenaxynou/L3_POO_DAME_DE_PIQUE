package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
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

    @Override
    public String toString() {
        return name + " (Score: " + score + ")";
    }

    public void receiveCards(Card[] cards) {
        // TODO Auto-generated method stub
       if(hand == null){
        hand = new ArrayList<>();
       }
       for(Card card : cards) {
        hand.add(card);
       }
    }
}
