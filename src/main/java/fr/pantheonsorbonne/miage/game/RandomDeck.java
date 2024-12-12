package fr.pantheonsorbonne.miage.game;

import java.util.*;

/**
 * Represents a Deck of cards
 */
public class RandomDeck implements Deck {

    private final static Random random = new Random();

    private final Queue<Card> deck = new LinkedList<>();


    public RandomDeck() {

        //generate all cards
        List<Card> cards = Card.getAllPossibleCards();
        //shuffle
        Collections.shuffle(cards);
        //associate with the deck
        this.deck.addAll(cards);

    }


    @Override
    public Card[] getCards(int length) {
        Card[] result = new Card[length];
        for (int i = 0; i < length; i++) {
            result[i] = this.deck.poll();
        }
        return result;
    }

    @Override 
    public void shuffle(){
        List<Card> shuffledList = new ArrayList<>(deck);
        Collections.shuffle(shuffledList);
        deck.clear();
        deck.addAll(shuffledList);
    }



    @Override
    public List<Card[]> dealCards(int players) {
        List<Card[]> hands = new ArrayList<>();
        int cardsPerPlayer = this.deck.size() / players;

        for (int i = 0; i < players; i++) {
            Card[] hand = new Card[cardsPerPlayer];
            for (int j = 0; j < cardsPerPlayer; j++) {
                hand[j] = this.deck.poll();
            }
            hands.add(hand);
        }
        return hands;
    }

    

    public List<Card> getAllCards() {
        return new ArrayList<>(this.deck);
    }

}
