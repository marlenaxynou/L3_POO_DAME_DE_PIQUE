package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

public class DeterministDeck implements Deck {

    private final Queue<Card> cards = new LinkedList<>();

    public DeterministDeck(Card... cards) {
        this.cards.addAll(Arrays.asList(cards));
    }

    @Override
    public Card[] getCards(int length) {
        Card[] res = new Card[length];
        for (int i = 0; i < length; i++) {
            res[i] = this.cards.poll();
        }
        return res;
    }

    @Override
    public void shuffle() {
    }

    @Override
    public List<Card[]> dealCards(int players) {
        List<Card[]> hands = new ArrayList<>();
        int cardsPerPlayer = this.cards.size() / players;
        
        for (int i = 0; i < players; i++) {
            Card[] hand = new Card[cardsPerPlayer];
            for (int j = 0; j < cardsPerPlayer; j++) {
                hand[j] = this.cards.poll();
            }
            hands.add(hand);
        }
        return hands;
    }

    @Override
    public List<Card> getAllCards() {
        return new ArrayList<>(this.cards);
    }
}
