package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a deck of cards, including shuffling and dealing functionality.
 */
public class Deck {

    private final List<Card> cards; // The deck of cards

    /**
     * Constructor to initialize a deck of 52 cards + 1 Joker.
     */
    public Deck() {
        this.cards = Card.getAllPossibleCards(); // Generates all 53 cards (including Joker)
    }

    /**
     * Shuffles the deck using the Collections.shuffle method.
     */
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    /**
     * Deals cards to players.
     *
     * @param numPlayers The number of players to deal cards to.
     * @return A list of hands, where each hand is a list of cards for a player.
     * @throws IllegalArgumentException If the number of players is invalid or cards can't be distributed evenly.
     */
    public List<List<Card>> deal(int numPlayers) {
        if (numPlayers <= 0 || this.cards.size() % numPlayers != 0) {
            throw new IllegalArgumentException("Cannot evenly distribute cards among players.");
        }

        List<List<Card>> hands = new ArrayList<>();
        int cardsPerPlayer = this.cards.size() / numPlayers;

        for (int i = 0; i < numPlayers; i++) {
            hands.add(new ArrayList<>(this.cards.subList(i * cardsPerPlayer, (i + 1) * cardsPerPlayer)));
        }

        return hands;
    }

    /**
     * Draws the top card from the deck.
     *
     * @return The top card.
     * @throws IllegalStateException If the deck is empty.
     */
    public Card draw() {
        if (this.cards.isEmpty()) {
            throw new IllegalStateException("No more cards in the deck.");
        }
        return this.cards.remove(0);
    }

    /**
     * Returns the number of remaining cards in the deck.
     *
     * @return The number of cards remaining.
     */
    public int size() {
        return this.cards.size();
    }

    /**
     * Returns the list of all cards in the deck (useful for debugging).
     *
     * @return The list of cards.
     */
    public List<Card> getCards() {
        return new ArrayList<>(this.cards); // Return a copy to preserve encapsulation
    }

    /**
     * Resets the deck to its original state (all 53 cards).
     */
    public void reset() {
        this.cards.clear();
        this.cards.addAll(Card.getAllPossibleCards());
    }
}
