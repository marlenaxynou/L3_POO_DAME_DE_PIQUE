package fr.pantheonsorbonne.miage.engine;

import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Player;

import java.util.*;


public abstract class DameDePiqueGameEngine {

    private final Deck deck;
    private final List<Player> players;
    private Card jokerCard;

    protected DameDePiqueGameEngine(Deck deck, List<Player> players) {
        this.deck = deck;
        this.players = players;
    }

    
    public void play() {
        initializeGame();

        // Main game loop
        while (!isGameOver()) {
            for (Player player : players) {
                playTurn(player);
            }
        }

        declareWinner(determineWinner());
    }
 

    private void initializeGame() {
        List<Card[]> hands = deck.dealCards(players.size());
        for (int i = 0; i < players.size(); i++) {
            players.get(i).receiveCards(hands.get(i));
        }

        
        determineJokerCard();
    }

    
    private void determineJokerCard() {
        List<Card> nonPointCards = new ArrayList<>();
        for (Card card : deck.getAllCards()) {
            if (!isPointCard(card)) {
                nonPointCards.add(card);
            }
        }
        jokerCard = nonPointCards.get(new Random().nextInt(nonPointCards.size()));
        System.out.println("Joker card is: " + jokerCard);
    }

    
    protected abstract void playTurn(Player player);

    
    protected abstract boolean isGameOver();

   
    protected abstract void declareWinner(Player winner);

   
    protected abstract Player determineWinner();


    private boolean isPointCard(Card card) {
        return card.getColor().equals("HEART") || (card.getColor().equals("SPADE") && card.getValue().getStringRepresentation().equals("Q"));
    }


    public Card getJokerCard() {
        return jokerCard;
    }

    
}
