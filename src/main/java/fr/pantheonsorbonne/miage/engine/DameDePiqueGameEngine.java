package fr.pantheonsorbonne.miage.engine;

import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Player;
import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.game.Card;
import java.util.List;

public abstract class DameDePiqueGameEngine {
    private final List<Player> players;
    private final Deck deck;
    private int round = 1;

    public DameDePiqueGameEngine(Deck deck, List<Player> players2) {
        this.deck = deck;
        this.players = players2;
    }

    public DameDePiqueGameEngine(Deck deck2, HostFacade hostFacade) {
        this.deck = deck2;
        this.players = ((DameDePiqueGameEngine) hostFacade).getPlayers();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void play() {
        System.out.println("=== Début de la partie de Dame de Pique ===");

        List<Card> deckCards = deck.getAllCards();
        List<List<Card>> hands = Card.dealCards(deckCards, players.size());
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setHand(hands.get(i));  
            System.out.println(players.get(i).getName() + " a les cartes suivantes : " + Card.cardsToString(hands.get(i)));
        }

        while (!isGameOver()) {
            System.out.println("\n--- Round " + round + " ---");
            for (Player player : players) {
                playTurn(player);
            }

            for (Player player : players) {
                player.calculerScore(); 
            }

            round++;
        }

        System.out.println("\n=== Fin de la partie ===");
        Player winner = determineWinner();
        declareWinner(winner);

        afficherScores();
    }

    protected abstract void playTurn(Player player);

    protected abstract boolean isGameOver();

    protected Player determineWinner() {
        Player winner = players.get(0);

        for (Player player : players) {
            if (player.getScore() < winner.getScore()) {
                winner = player;
            }
        }

        return winner;
    }

    protected abstract void declareWinner(Player winner);

    protected abstract List<String> getInitialPlayers(); 

    public void afficherScores() {
        System.out.println("\n=== Scores à la fin de la partie ===");
        for (Player player : players) {
            System.out.println(player.getName() + " (Score: " + player.getScore() + ")");
        }
    }

    public void exchangeCards() {
        
        int round = this.round;
        List<Player> players = getPlayers();

        if (round == 1) {
            passCards(players, 1); 
        } else if (round == 2) {
            passCards(players, -1); 
        } else if (round == 3) {
            passCards(players, 2); 
        }
    }

    private void passCards(List<Player> players, int direction) {
        
        Player currentPlayer = players.get(0);
        List<Card> cardsToPass = currentPlayer.selectCardsToPass();

        if (direction == 1) { 
            for (int i = 0; i < 3; i++) {
                players.get(1).receiveCard(cardsToPass.get(i));
            }
        } else if (direction == -1) { 
            for (int i = 0; i < 3; i++) {
                players.get(players.size() - 1).receiveCard(cardsToPass.get(i));
            }
        } else if (direction == 2) {
            int oppositeIndex = players.size() / 2;
            for (int i = 0; i < 3; i++) {
                players.get(oppositeIndex).receiveCard(cardsToPass.get(i)); 
            }
        }
    }
}
