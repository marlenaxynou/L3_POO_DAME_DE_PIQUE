package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;

public abstract class Deck {
    protected final List<Card> cards = new ArrayList<>();

    public Deck() {
        initializeDeck();
    }

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

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public List<Card> getAllCards() {
        return new ArrayList<>(cards);
    }

    public abstract List<Card[]> dealCards(int numberOfPlayers);

    public abstract Card drawCard() throws NoMoreCardException;
}