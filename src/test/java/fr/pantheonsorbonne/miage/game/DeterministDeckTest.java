package fr.pantheonsorbonne.miage.game;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.exception.NoMoreCardException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DeterministDeckTest extends DeckTest {

    @Override
    protected Deck getDeck() {
        return new DeterministDeck(Card.getAllPossibleCards());
    }

    @Test
    void testDealCards() {
        DeterministDeck deck = new DeterministDeck(Card.getAllPossibleCards());
        List<Card[]> hands = deck.dealCards(4);
        assertEquals(4, hands.size());
        for (Card[] hand : hands) {
            assertEquals(13, hand.length);
        }
    }

    @Test
    void testDrawCard() throws NoMoreCardException {
        DeterministDeck deck = new DeterministDeck(Card.getAllPossibleCards());
        Card firstCard = deck.drawCard();
        assertNotNull(firstCard);
        assertEquals(51, deck.getAllCards().size());
    }

    @Test
    void testDrawAllCards() throws NoMoreCardException {
        DeterministDeck deck = new DeterministDeck(Card.getAllPossibleCards());
        for (int i = 0; i < 52; i++) {
            deck.drawCard();
        }
        assertThrows(NoMoreCardException.class, deck::drawCard);
    }
}