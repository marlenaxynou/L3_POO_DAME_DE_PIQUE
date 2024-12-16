package fr.pantheonsorbonne.miage.engine;

import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Player;

import java.util.List;

public abstract class DameDePiqueGameEngine {
    private final List<Player> players;
    private int round = 1; // Nouveau compteur de rounds

    public DameDePiqueGameEngine(Deck deck, List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void play() {
        System.out.println("=== Début de la partie de Dame de Pique ===");

        while (!isGameOver()) {
            System.out.println("\n--- Round " + round + " ---");
            for (Player player : players) {
                playTurn(player);
            }
            round++;
        }

        System.out.println("\n=== Fin de la partie ===");
        Player winner = determineWinner();
        declareWinner(winner);
    }

    protected abstract void playTurn(Player player);

    protected abstract boolean isGameOver();

    protected abstract Player determineWinner();

    protected abstract void declareWinner(Player winner);
}
