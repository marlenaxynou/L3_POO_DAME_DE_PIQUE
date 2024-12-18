package fr.pantheonsorbonne.miage.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

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

    
    public int calculerScore() {
        int score = 0;

        if (hand != null) {
            for (Card card : hand) {
                CardValue cardValue = card.getValue();
                CardColor cardColor = card.getColor();

                if (cardValue == CardValue.QUEEN && cardColor == CardColor.SPADES) {
                    score += 13;
                }
                else if (cardColor == CardColor.HEARTS) {
                    score += 1;
                }
                else if (cardValue == CardValue.JOKER) {
                    score += 1;
                }
            }
        }

        return score;
    }

    
    public boolean hasNoCards() {
        return hand == null || hand.isEmpty();
    }

    public boolean hasGrandChelem() {
        boolean hasQueenOfSpades = false;
        int heartCount = 0;

        for (Card card : hand) {
            if (card.getColor() == CardColor.HEARTS) {
                heartCount++;
            } else if (card.getColor() == CardColor.SPADES && card.getValue() == CardValue.QUEEN) {
                hasQueenOfSpades = true;
            }
        }

        return hasQueenOfSpades && heartCount == 13;
    }

    /**
     * Selects cards to pass to another player.
     * 
     * @return The cards to pass.
     */
    public List<Card> selectCardsToPass() {
        if (hand == null || hand.size() < 3) {
            throw new IllegalStateException("Not enough cards to pass.");
        }

        List<Card> sortedHand = new ArrayList<>(hand);
        sortedHand.sort(Comparator.comparing(Card::getValue).reversed());

        List<Card> cardsToPass = new ArrayList<>(sortedHand.subList(0, 3));

        hand.removeAll(cardsToPass);

        return cardsToPass;
    
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
