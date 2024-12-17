package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.exception.NoMoreCardException;

public class StandardDeck extends Deck {
    private final List<Card> cards;

    public StandardDeck() {
        this.cards = new ArrayList<>();
        initializeDeck();
        shuffle();
    }

    @Override
    protected void initializeDeck() {
        for (CardColor color : CardColor.values()) {
            for (CardValue value : CardValue.values()) {
                cards.add(new Card(color, value));
            }
        }
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dealCards'");
    }
}
