package fr.pantheonsorbonne.miage.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fr.pantheonsorbonne.miage.exception.NoMoreCardException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class DeckTest {

    private Deck deck;

    @BeforeEach
    void setup() {
        this.deck = this.getDeck();
    }

    protected abstract Deck getDeck();

    @Test
    void get1Cards() {
        Card[] cards = deck.getCards(1);
        assertEquals(1, cards.length);
    }

    @Test
    void getnCards() {
        Card[] cards = deck.getCards(10);
        assertEquals(10, cards.length);
    }

    @Test
    void getCard() throws NoMoreCardException {
        Card card = deck.getCard();
        Card newCard = null;
        do {
            assertNotEquals(card, newCard);
            newCard = deck.getCard();
        } while (newCard != null);
    }

    @Test
    void testDrawAllCards() throws NoMoreCardException {
        while (true) {
            deck.getCard();
        }
    }

    @Test
    void testDrawCard() throws NoMoreCardException {
        for (int i = 0; i < 52; i++) {
            deck.getCard();
        }
        assertThrows(NoMoreCardException.class, () -> {
            deck.getCard();
        });
    }
}