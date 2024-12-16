package fr.pantheonsorbonne.miage.engine.local;

import fr.pantheonsorbonne.miage.engine.DameDePiqueGameEngine;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Player;
import fr.pantheonsorbonne.miage.game.RandomDeck;

import java.util.*;

public class LocalDameDePiqueGame extends DameDePiqueGameEngine {

    private final Map<Player, Queue<Card>> playerHands = new HashMap<>();

    public LocalDameDePiqueGame(RandomDeck deck, List<Player> players) {
        super(deck, players);
        distributeCards(deck, players);
    }

    private void distributeCards(RandomDeck deck, List<Player> players) {
        List<List<Card>> hands = Card.dealCards(deck.getAllCards(), players.size());
        for (int i = 0; i < players.size(); i++) {
            playerHands.put(players.get(i), new LinkedList<>(hands.get(i)));
        }
    }

    public static void main(String[] args) {
        List<Player> players = Arrays.asList(
                new Player("Joueur1"),
                new Player("Joueur2"),
                new Player("Joueur3"),
                new Player("Joueur4")
        );

        RandomDeck deck = new RandomDeck();
        deck.shuffle();

        System.out.println("=== Début de la partie de Dame de Pique ===\n");
        LocalDameDePiqueGame game = new LocalDameDePiqueGame(deck, players);
        game.play();
        System.exit(0);
    }

    @Override
    protected void playTurn(Player player) {
        System.out.println("\n" + player.getName() + " joue son tour :");

        System.out.println("Cartes en main : " + cardsToString(playerHands.get(player)));

        Card playedCard = playerHands.get(player).poll();
        if (playedCard != null) {
            System.out.println(player.getName() + " a joué la carte : " + playedCard.toFancyString());
        } else {
            System.out.println(player.getName() + " n'a plus de cartes.");
        }
    }

    @Override
    protected boolean isGameOver() {
        for (Queue<Card> hand : playerHands.values()) {
            if (!hand.isEmpty()) {
                return false; 
            }
        }
        return true;
    }
    

    @Override
    protected Player determineWinner() {
        Player winner = getPlayers().get(0);
    
        for (Player player : getPlayers()) {
            if (player.getScore() < winner.getScore()) {
                winner = player; 
            }
        }
    
        return winner;
    }
    

    @Override
    protected void declareWinner(Player winner) {
        System.out.println("\n>>> Le gagnant est " + winner.getName() + " avec un score de " + winner.getScore() + " points ! <<<");
    }

    private String cardsToString(Queue<Card> cards) {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.toFancyString()).append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    protected List<String> getInitialPlayers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInitialPlayers'");
    }
}
