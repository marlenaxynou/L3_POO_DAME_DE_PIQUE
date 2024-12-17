package fr.pantheonsorbonne.miage.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;

public class Player {
    private String name;
    private List<Card> hand;
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
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

    /**
     * Calculate the score for this player based on the current hand.
     */
    public int calculerScore() {
        int score = 0;

        if (hand != null) {
            for (Card card : hand) {
                CardValue cardValue = card.getValue();
                CardColor cardColor = card.getColor();

                // Check for Queen of Spades
                if (cardValue == CardValue.QUEEN && cardColor == CardColor.SPADES) {
                    score += 13;
                }
                // Check for Heart cards
                else if (cardColor == CardColor.HEARTS) {
                    score += 1;
                }
                // Check for Joker
                else if (cardValue == CardValue.JOKER) {
                    score += 1;
                }
            }
        }

        return score;
    }

    /**
     * Checks if the player has no cards left in hand.
     */
    public boolean hasNoCards() {
        return hand == null || hand.isEmpty();
    }

    /**
     * Selects cards to pass based on the game's rules.
     * For now, this method is a placeholder.
     */
    public List<Card> selectCardsToPass() {
        // TODO: Implement logic for selecting cards to pass.
        throw new UnsupportedOperationException("Unimplemented method 'selectCardsToPass'");
    }

    /**
     * Receives a card passed by another player.
     * 
     * @param card The card to receive.
     */
    public void receiveCard(Card card) {
        if (hand == null) {
            hand = new ArrayList<>();
        }
        hand.add(card);
    }

    /**
     * Notifies this player of the winner of the game.
     * 
     * @param winner The player who won.
     */
    public void notifyWinner(Player winner) throws IOException {
        System.out.println(this.name + " is notified that " + winner.getName() + " won.");
    }

    @Override
    public String toString() {
        return name + " (Score: " + score + ")";
    }
}
