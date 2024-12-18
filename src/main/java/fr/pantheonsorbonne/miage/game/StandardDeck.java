package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.exception.NoMoreCardException;

public class StandardDeck extends Deck {

    public StandardDeck() {
        super();
        shuffle();
    }

    @Override
    protected void initializeDeck() {
        for (CardColor color : CardColor.values()) {
            if (color != CardColor.NONE) { 
                for (CardValue value : CardValue.values()) {
                    if (value != CardValue.JOKER) { 
                        cards.add(new Card(color, value));
                    }
                }
            }
        }
        cards.add(new Card(CardColor.NONE, CardValue.JOKER));
    }

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
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
        return cards.remove(cards.size() - 1);
    }

    @Override
    public List<Card[]> dealCards(int numberOfPlayers) {
        int cardsPerPlayer = cards.size() / numberOfPlayers;
        List<Card[]> hands = new ArrayList<>();
    
        for (int i = 0; i < numberOfPlayers; i++) {
            Card[] hand = new Card[cardsPerPlayer];
            for (int j = 0; j < cardsPerPlayer; j++) {
                hand[j] = cards.remove(cards.size() - 1);
            }
            hands.add(hand);
        }
        return hands;
    }
}