package fr.pantheonsorbonne.miage.engine.net;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * This is the network player class for the Dame de Pique game.
 */
public class DameDePiqueNetworkPlayer {

    static final String playerId = "Player-" + new Random().nextInt();
    static final Deque<Card> hand = new LinkedList<>();
    static final PlayerFacade playerFacade = Facade.getFacade();
    static Game dameDePiqueGame;

    public static void main(String[] args) {
        playerFacade.waitReady();
        playerFacade.createNewPlayer(playerId);
        dameDePiqueGame = playerFacade.autoJoinGame("DAME_DE_PIQUE");

        while (true) {
            // Wait for the game command
            GameCommand command = playerFacade.receiveGameCommand(dameDePiqueGame);
            switch (command.name()) {
                case "dealCards":
                    handleDealCards(command);
                    break;
                case "playCard":
                    System.out.println("My hand: " + hand.stream().map(Card::toFancyString).collect(Collectors.joining(" ")));
                    handlePlayCard(command);
                    break;
                case "passCards":
                    handlePassCards(command);
                    break;
                case "gameOver":
                    handleGameOverCommand(command);
                    break;
            }
        }
    }

    // Handle when cards are dealt to the player
    private static void handleDealCards(GameCommand command) {
        for (Card card : Card.stringToCards(command.body())) {
            hand.offerLast(card);
        }
    }

    // Handle when the player needs to play a card
    private static void handlePlayCard(GameCommand command) {
        if (command.params().get("playerId").equals(playerId)) {
            if (!hand.isEmpty()) {
                // Play a card and send it to all players
                Card cardToPlay = hand.pollFirst();
                playerFacade.sendGameCommandToAll(dameDePiqueGame, new GameCommand("playedCard", cardToPlay.toString()));
            } else {
                playerFacade.sendGameCommandToAll(dameDePiqueGame, new GameCommand("outOfCards", playerId));
            }
        }
    }

    // Handle when the player is asked to pass 3 cards (depending on the round)
    private static void handlePassCards(GameCommand command) {
        // Assuming we receive a list of 3 cards to pass
        String passedCards = command.body();
        List<Card> cardsToPass = Card.stringToCards(passedCards);
        // Logic to pass 3 cards to the next player (you will need to define which direction)
        playerFacade.sendGameCommandToAll(dameDePiqueGame, new GameCommand("cardsPassed", cardsToPass.stream().map(Card::toString).collect(Collectors.joining(" "))));
    }

    // Handle the game over state
    private static void handleGameOverCommand(GameCommand command) {
        if (command.body().equals("win")) {
            System.out.println("I won the game!");
        } else {
            System.out.println("I lost the game :-(");
        }
        System.exit(0);
    }
}
