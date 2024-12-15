package fr.pantheonsorbonne.miage.engine.local;

import fr.pantheonsorbonne.miage.engine.DameDePiqueGameEngine;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Player;
import fr.pantheonsorbonne.miage.game.RandomDeck;

import java.util.*;
import java.util.stream.Collectors;

public class LocalDameDePiqueGame extends DameDePiqueGameEngine {

    private final List<Player> initialPlayers;
    private final Map<Player, Queue<Card>> playerCards = new HashMap<>();
    private Card jokerCard;

    public LocalDameDePiqueGame(Deck deck, List<Player> initialPlayers) {
        super(deck, initialPlayers);
        this.initialPlayers = initialPlayers;
        for (Player player : initialPlayers) {
            playerCards.put(player, new LinkedList<>());
        }
    }

    public static void main(String... args) {
        
        List<Player> players = Arrays.asList(new Player("Joueur1"), new Player("Joueur2"), new Player("Joueur3"));
        LocalDameDePiqueGame game = new LocalDameDePiqueGame(new RandomDeck(), players);
        game.play();
        System.exit(0);
    }

    @Override
    protected void initializeGame() {
        
        List<Card[]> hands = getDeck().dealCards(initialPlayers.size());
        for (int i = 0; i < initialPlayers.size(); i++) {
            initialPlayers.get(i).receiveCards(hands.get(i));
        }

        // Déterminer la carte Joker
        determineJokerCard();
    }

    @Override
    protected void determineJokerCard() {
        List<Card> nonPointCards = new ArrayList<>();
        for (Card card : getDeck().getAllCards()) {
            if (!isPointCard(card)) {
                nonPointCards.add(card);
            }
        }
        jokerCard = nonPointCards.get(new Random().nextInt(nonPointCards.size()));
        System.out.println("Joker card is: " + jokerCard);
    }

    @Override
    protected void playTurn(Player player) {
        
        System.out.println(player.getName() + " is playing their turn");
        
    }

    @Override
    protected boolean isGameOver() {
        
        return false;  
    }

    @Override
    protected void declareWinner(Player winner) {
        System.out.println(winner.getName() + " has won the game!");
    }

    @Override
    protected Player determineWinner() {
        
        return initialPlayers.get(0);  
    }

    private boolean isPointCard(Card card) {
        
        return card.getColor().equals("HEART") || (card.getColor().equals("SPADE") && card.getValue().getStringRepresentation().equals("Q"));
    }

    @Override
    protected void giveCardsToPlayer(Collection<Card> roundStack, Player winner) {
        
        List<Card> cards = new ArrayList<>(roundStack);
        Collections.shuffle(cards);
        playerCards.get(winner).addAll(cards);
    }

    @Override
    protected Card getCardFromPlayer(Player player) {
        
        if (!playerCards.containsKey(player) || playerCards.get(player).isEmpty()) {
            return null;  
        }
        return playerCards.get(player).poll();
    }


    public Card getJokerCard() {
        return jokerCard;
    }
}
