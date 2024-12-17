package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;

public class Card {

    private final CardColor color;  
    private final CardValue value;

    public Card(CardColor color, CardValue value) {
        if (value == CardValue.JOKER) {
            this.color = CardColor.NONE;
        } else {
            this.color = color;
        }
        this.value = value;
    }

    public CardColor getColor() {
        return color;
    }

    public CardValue getValue() {
        return value;
    }

    public boolean estDameDePique() {
        return this.color == CardColor.SPADES && this.value == CardValue.QUEEN;
    }

    public static List<Card> getAllPossibleCards() {
        List<Card> deck = new ArrayList<>();

        for (CardColor color : CardColor.values()) {
            if (color != CardColor.NONE) { 
                for (CardValue value : CardValue.values()) {
                    if (value != CardValue.JOKER) { 
                        deck.add(new Card(color, value));
                    }
                }
            }
        }

        deck.add(new Card(CardColor.NONE, CardValue.JOKER));

        return deck;
    }

    public static void shuffleDeck(List<Card> deck) {
        Collections.shuffle(deck);
    }

    public static List<List<Card>> dealCards(List<Card> deck, int numPlayers) {
        List<List<Card>> hands = new ArrayList<>();
        int cardsPerPlayer = deck.size() / numPlayers;

        for (int i = 0; i < numPlayers; i++) {
            hands.add(new ArrayList<>(deck.subList(i * cardsPerPlayer, (i + 1) * cardsPerPlayer)));
        }

        return hands;
    }

    public static String cardsToString(List<Card> cards) {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.toString()).append(";");
        }
        return sb.toString().replaceAll(";$", "");
    }

    public static List<Card> stringToCards(String cardString) {
        List<Card> cards = new ArrayList<>();
        String[] cardParts = cardString.split(";");
        for (String part : cardParts) {
            cards.add(Card.valueOf(part));
        }
        return cards;
    }

    public static Card valueOf(String cardStr) {
        if (cardStr.equals("J")) {
            return new Card(CardColor.NONE, CardValue.JOKER);
        }

        CardValue value = CardValue.fromSymbol(cardStr.substring(0, cardStr.length() - 1));
        CardColor color = CardColor.fromSymbol(cardStr.substring(cardStr.length() - 1));
        return new Card(color, value);
    }

    @Override
    public String toString() {
        return this.value.getSymbol() + (this.color != CardColor.NONE ? this.color.getSymbol() : "");
    }

    public String toFancyString() {
        return this.value.getSymbol() + this.color.getUnicodeSymbol();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return color == card.color && value == card.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, value);
    }
}