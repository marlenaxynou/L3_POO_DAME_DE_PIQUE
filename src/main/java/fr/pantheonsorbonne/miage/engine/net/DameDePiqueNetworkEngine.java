package fr.pantheonsorbonne.miage.engine.net;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.engine.DameDePiqueGameEngine;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Player;
import fr.pantheonsorbonne.miage.game.StandardDeck;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;

import java.util.*;
import java.util.stream.Collectors;

public class DameDePiqueNetworkEngine extends DameDePiqueGameEngine {
    private static final int PLAYER_COUNT = 4; 
    private final Game dameDePiqueGame;

    public DameDePiqueNetworkEngine(Deck deck, List<Player> players, Game dameDePiqueGame) {
        super(deck, players);
        this.dameDePiqueGame = dameDePiqueGame;
    }

    public static void main(String[] args) {
        HostFacade hostFacade = Facade.getFacade();
        hostFacade.waitReady();

        hostFacade.createNewPlayer("Host");

        Game dameDePiqueGame = hostFacade.createNewGame("DAME_DE_PIQUE");

        hostFacade.waitForExtraPlayerCount(PLAYER_COUNT);

        List<Player> players = new ArrayList<>();
        for (String playerName : dameDePiqueGame.getPlayers()) {
            players.add(new Player(playerName));
        }

        DameDePiqueNetworkEngine host = new DameDePiqueNetworkEngine(new StandardDeck(), players, dameDePiqueGame);
        host.play();
        System.exit(0);
    }

    @Override
    protected List<String> getInitialPlayers() {
        return new ArrayList<>(this.dameDePiqueGame.getPlayers());
    }

    @Override
    protected void playTurn(Player player) {
        System.out.println("\n" + player.getName() + " joue son tour :");

        List<Card> playerHand = player.getHand();
        System.out.println("Cartes en main : " + playerHand.stream().map(Card::toFancyString).collect(Collectors.joining(" ")));

        if (!playerHand.isEmpty()) {
            Card playedCard = playerHand.remove(0);
            System.out.println(player.getName() + " a joué la carte : " + playedCard.toFancyString());

            if (playedCard.getColor() == CardColor.HEARTS) {
                player.addScore(1);
            } else if (playedCard.getValue() == CardValue.QUEEN && playedCard.getColor() == CardColor.SPADES) {
                player.addScore(13);
            } else if (playedCard.getValue() == CardValue.JOKER) {
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
        for (Player player : getPlayers()) {
            if (!player.hasNoCards()) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void declareWinner(Player winner) {
        System.out.println("\n>>> Le gagnant est " + winner.getName() + " avec un score de " + winner.getScore() + " points ! <<<");
    }
}