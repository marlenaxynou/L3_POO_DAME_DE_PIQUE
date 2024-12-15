package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




public class DeterministDeck extends Deck {

    private final List<Card> predeterminedCards;

    /**
     * Constructor to initialize a deterministic deck with a predefined order of cards.
     *
     * @param predeterminedCards The fixed order of cards for the deck.
     */
    public DeterministDeck(List<Card> predeterminedCards) {
        this.predeterminedCards = new ArrayList<>(predeterminedCards);
    }

    /**
     * Overrides the shuffle method to keep the deck in a predetermined order.
     */
    @Override
    public void shuffle() {
        // Do nothing to maintain the predetermined order.
    }

    /**
     * Deals cards to players in the predetermined order.
     *
     * @param numPlayers The number of players to deal cards to.
     * @return A list of hands, where each hand is a list of cards for a player.
     * @throws IllegalArgumentException If the number of players is invalid or cards can't be distributed evenly.
     */
    @Override
    public List<List<Card>> deal(int numPlayers) {
        if (numPlayers <= 0 || this.predeterminedCards.size() % numPlayers != 0) {
            throw new IllegalArgumentException("Cannot evenly distribute cards among players.");
        }

        List<List<Card>> hands = new ArrayList<>();
        int cardsPerPlayer = this.predeterminedCards.size() / numPlayers;

        for (int i = 0; i < numPlayers; i++) {
            hands.add(new ArrayList<>(this.predeterminedCards.subList(i * cardsPerPlayer, (i + 1) * cardsPerPlayer)));
        }

        return hands;
    }

    /**
     * Draws the top card from the predetermined deck.
     *
     * @return The top card.
     * @throws IllegalStateException If the deck is empty.
     */
    @Override
    public Card draw() {
        if (this.predeterminedCards.isEmpty()) {
            throw new IllegalStateException("No more cards in the predetermined deck.");
        }
        return this.predeterminedCards.remove(0);
    }

    /**
     * Returns the number of remaining cards in the predetermined deck.
     *
     * @return The number of cards remaining.
     */
    @Override
    public int size() {
        return this.predeterminedCards.size();
    }

    /**
     * Returns the list of all cards in the predetermined deck (useful for debugging).
     *
     * @return The list of predetermined cards.
     */
    @Override
    public List<Card> getCards() {
        return new ArrayList<>(this.predeterminedCards); // Return a copy to preserve encapsulation
    }

    /**
     * Resets the deck to its original predetermined state.
     */
    @Override
    public void reset() {
        this.predeterminedCards.clear();
        this.predeterminedCards.addAll(Card.getAllPossibleCards());
    }
}
