package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.exception.NoMoreCardException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeterministDeck extends Deck {
    private List<Card> cards;

    public DeterministDeck(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    @Override
    public List<Card> getAllCards() {
        return new ArrayList<>(cards);
    }

    @Override
    public Card drawCard() throws NoMoreCardException {
        if (cards.isEmpty()) {
            throw new NoMoreCardException();
        }
        return cards.remove(0);
    }

    @Override
    public List<Card[]> dealCards(int numPlayers) {
        List<Card[]> hands = new ArrayList<>();
        int cardsPerPlayer = cards.size() / numPlayers;

        for (int i = 0; i < numPlayers; i++) {
            Card[] hand = new Card[cardsPerPlayer];
            for (int j = 0; j < cardsPerPlayer; j++) {
                hand[j] = cards.remove(0);
            }
            hands.add(hand);
        }

        return hands;
    }

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }
}