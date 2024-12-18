package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.exception.NoMoreCardException;

public class DeterministDeck extends Deck {

    private final List<Card> predeterminedCards;

    public DeterministDeck(List<Card> predeterminedCards) {
        this.predeterminedCards = new ArrayList<>(predeterminedCards);
    }

    @Override
    public void shuffle() {
    }

    @Override
    public List<Card[]> dealCards(int numberOfPlayers) {
        if (numberOfPlayers <= 0 || this.predeterminedCards.size() % numberOfPlayers != 0) {
            throw new IllegalArgumentException("Cannot evenly distribute cards among players.");
        }

        List<Card[]> hands = new ArrayList<>();
        int cardsPerPlayer = this.predeterminedCards.size() / numberOfPlayers;

        for (int i = 0; i < numberOfPlayers; i++) {
            hands.add(this.predeterminedCards.subList(i * cardsPerPlayer, (i + 1) * cardsPerPlayer).toArray(new Card[0]));
        }

        return hands;
    }

    @Override
    public Card drawCard() throws NoMoreCardException {
        if (this.predeterminedCards.isEmpty()) {
            throw new NoMoreCardException();
        }
        return this.predeterminedCards.remove(0);
    }
}