package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;

/**
 * Represents a Deck of cards
 */
public class RandomDeck implements Deck {

    private final static Random random = new Random();

    private final Queue<Card> deck = new LinkedList<>();
    
        
    
    
        public RandomDeck() {
    
            //generate all cards
            List<Card> cards = Card.getAllPossibleCards();
            cards.add(new Card(CardValue.JOKER, CardColor.NONE)); // Ajout du Joker
            //shuffle
            Collections.shuffle(cards);
            //associate with the deck
            for (int i = 0; i < cards.size(); i++) {
                this.deck.offer(cards.get(i));
            }
    
            this.joker = designerJoker(cards);

    }

    private Card designerJoker(List<Card> cards) {
        
        List<Card> pasDePointsCartes = new ArrayList<>();
        for (Card card : cards) {
            if (card.getColor() != CardColor.HEART && card.getValue() != CardValue.QUEEN) {
                pasDePointsCartes.add(card);
            }
        }
        return pasDePointsCartes.get(random.nextInt(pasDePointsCartes.size()));
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
