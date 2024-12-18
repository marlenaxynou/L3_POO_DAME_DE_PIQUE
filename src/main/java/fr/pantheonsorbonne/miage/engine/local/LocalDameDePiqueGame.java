package fr.pantheonsorbonne.miage.engine.local;

import fr.pantheonsorbonne.miage.engine.DameDePiqueGameEngine;
import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Player;
import fr.pantheonsorbonne.miage.game.RandomDeck;

import java.util.*;

public class LocalDameDePiqueGame extends DameDePiqueGameEngine {

    private final Map<Player, Queue<Card>> playerHands = new HashMap<>();
    private boolean heartsBroken = false;  // Track if Hearts are broken
    private Card jokerCard; // Track the Joker card for this round
    private int round = 1; // Track the current round

    public LocalDameDePiqueGame(RandomDeck deck, List<Player> players) {
        super(deck, players);
        distributeCards(deck, players);
    }

    private void distributeCards(RandomDeck deck, List<Player> players) {
        List<Card> allCards = deck.getAllCards();
        this.jokerCard = pickJoker(allCards); // Select the Joker card

        // Add the joker card to the deck
        allCards.add(jokerCard);

        List<List<Card>> hands = Card.dealCards(allCards, players.size());
        for (int i = 0; i < players.size(); i++) {
            playerHands.put(players.get(i), new LinkedList<>(hands.get(i)));
        }
    }

    // Method to select the joker card randomly from cards that do not score points
    private Card pickJoker(List<Card> allCards) {
        List<Card> nonScoringCards = new ArrayList<>();
    
        // Select cards that do not score points
        for (Card card : allCards) {
            if (card.getColor() != CardColor.HEARTS && card.getValue() != CardValue.QUEEN) {
                nonScoringCards.add(card);
            }
        }
    
        // Return a random non-scoring card as the joker and set its color to NONE
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
    
        // Print player's current hand
        System.out.println("Cartes en main : " + cardsToString(playerHands.get(player)));
    
        // Player plays a card (the first card in hand for simplicity)
        Card playedCard = playerHands.get(player).poll();
    
        if (playedCard != null) {
            System.out.println(player.getName() + " a joué la carte : " + playedCard.toFancyString());
    
            // If Hearts are broken, a Heart card can be played
            if (playedCard.getColor() == CardColor.HEARTS && !heartsBroken) {
                heartsBroken = true;  // Mark that Hearts are broken
            }
    
            // Add points based on the card played
            if (playedCard.getColor() == CardColor.HEARTS) {
                player.addScore(1);  // +1 point for Hearts
            } else if (playedCard.getValue() == CardValue.QUEEN && playedCard.getColor() == CardColor.SPADES) {
                player.addScore(13); // +13 points for Queen of Spades
            } else if (playedCard == jokerCard) {
                player.addScore(1); // Joker counts as 1 point
            }
        } else {
            System.out.println(player.getName() + " n'a plus de cartes.");
        }
    
        // Print the scores after each player's turn
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
                return false;  // The game is not over if any player still has cards
            }
        }
        return true;  // The game is over if all hands are empty
    }

    @Override
    protected Player determineWinner() {
        Player winner = getPlayers().get(0);

        for (Player player : getPlayers()) {
            if (player.getScore() < winner.getScore()) {
                winner = player;  // Player with the lowest score wins
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
                case 1: // Pass to the left
                    targetPlayer = players.get((i + 1) % players.size());
                    break;
                case 2: // Pass to the right
                    targetPlayer = players.get((i - 1 + players.size()) % players.size());
                    break;
                case 3: // Pass across
                    targetPlayer = players.get((i + 2) % players.size());
                    break;
                default: // No passing
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
}