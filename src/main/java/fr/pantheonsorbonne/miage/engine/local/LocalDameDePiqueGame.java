package fr.pantheonsorbonne.miage.engine.local;

import fr.pantheonsorbonne.miage.engine.DameDePiqueGameEngine;
import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.DeterministDeck;
import fr.pantheonsorbonne.miage.game.Player;
import fr.pantheonsorbonne.miage.game.RandomDeck;

import java.util.*;

public class LocalDameDePiqueGame extends DameDePiqueGameEngine {

    private final Map<Player, Queue<Card>> playerHands = new HashMap<>();
    private boolean heartsBroken = false;  
    private Card jokerCard; 
    private int round = 1; 

    public LocalDameDePiqueGame(RandomDeck deck, List<Player> players) {
        super(deck, players);
        distributeCards(deck, players);
    }
    
    public LocalDameDePiqueGame(DeterministDeck deck, List<Player> players) {

        super(deck, players);

    }

    private void distributeCards(RandomDeck deck, List<Player> players) {
        List<Card> allCards = deck.getAllCards();
        this.jokerCard = pickJoker(allCards); 

        
        allCards.add(jokerCard);

        List<List<Card>> hands = Card.dealCards(allCards, players.size());
        for (int i = 0; i < players.size(); i++) {
            playerHands.put(players.get(i), new LinkedList<>(hands.get(i)));
        }
    }

    private Card pickJoker(List<Card> allCards) {
        List<Card> nonScoringCards = new ArrayList<>();
    
        for (Card card : allCards) {
            if (card.getColor() != CardColor.HEARTS && card.getValue() != CardValue.QUEEN) {
                nonScoringCards.add(card);
            }
        }
    
        Card joker = nonScoringCards.get(new Random().nextInt(nonScoringCards.size()));
        allCards.remove(joker);
        return new Card(CardColor.NONE, joker.getValue());
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
    
            if (playedCard.getColor() == CardColor.HEARTS && !heartsBroken) {
                heartsBroken = true;  
            }
    
            if (playedCard.getColor() == CardColor.HEARTS) {
                player.addScore(1); 
            } else if (playedCard.getValue() == CardValue.QUEEN && playedCard.getColor() == CardColor.SPADES) {
                player.addScore(13); 
            } else if (playedCard == jokerCard) {
                player.addScore(1);
            }
        } else {
            System.out.println(player.getName() + " n'a plus de cartes.");
        }
    
        printScores();
    }

    private void printScores() {
        System.out.println("\n=== Scores à la fin de cette manche ===");
        for (Player player : getPlayers()) {
            System.out.println(player.getName() + " (Score: " + player.getScore() + ")");
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
        throw new UnsupportedOperationException("Unimplemented method 'getInitialPlayers'");
    }

    private void passCards() {
        List<Player> players = getPlayers();
        int direction = round % 4;

        for (int i = 0; i < players.size(); i++) {
            Player currentPlayer = players.get(i);
            List<Card> cardsToPass = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                cardsToPass.add(playerHands.get(currentPlayer).poll());
            }

            Player targetPlayer;
            switch (direction) {
                case 1: 
                    targetPlayer = players.get((i + 1) % players.size());
                    break;
                case 2:
                    targetPlayer = players.get((i - 1 + players.size()) % players.size());
                    break;
                case 3: 
                    targetPlayer = players.get((i + 2) % players.size());
                    break;
                default: 
                    continue;
            }

            playerHands.get(targetPlayer).addAll(cardsToPass);
        }
    }

    @Override
    public void play() {
        while (!isGameOver()) {
            System.out.println("\n=== Round " + round + " ===");
            if (round <= 3) {
                passCards();
            }
            for (Player player : getPlayers()) {
                playTurn(player);
            }
            round++;
        }
        Player winner = determineWinner();
        declareWinner(winner);
    }
    //testcommit
}