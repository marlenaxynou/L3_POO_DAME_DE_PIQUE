package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

class CardTest {

    @Test
    void cardsToString() {
        {
            Card card = new Card(CardColor.CLUBS, CardValue.ACE);
            assertEquals("1C", card.toString());
        }
        {
            Card card = new Card(CardColor.HEARTS, CardValue.TEN);
            assertEquals("10H", card.toString());
        }
    }

    @Test
    void getValue() {
        assertEquals(CardValue.ACE, Card.valueOf("1S").getValue());
    }

    @Test
    void getColor() {
        assertEquals(CardColor.SPADES, Card.valueOf("1S").getColor());
    }

    @Test
    void stringToCards() {
        List<Card> cards = Card.stringToCards("10S;KH");
        assertEquals(new Card(CardColor.SPADES, CardValue.TEN), cards.get(0));
        assertEquals(new Card(CardColor.HEARTS, CardValue.KING), cards.get(1));
    }

    @Test
    void valueOf() {
        assertEquals(CardValue.TEN, Card.valueOf("10D").getValue());
        assertEquals(CardColor.DIAMONDS, Card.valueOf("10D").getColor());
    }
}