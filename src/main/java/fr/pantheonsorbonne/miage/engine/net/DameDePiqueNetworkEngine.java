package fr.pantheonsorbonne.miage.engine.net;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.engine.DameDePiqueGameEngine;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Player;
import fr.pantheonsorbonne.miage.game.StandardDeck;
import fr.pantheonsorbonne.miage.model.Game;
import java.util.*;


public class DameDePiqueNetworkEngine extends DameDePiqueGameEngine {
    private static final int PLAYER_COUNT = 4; // Assuming 4 players are needed
    private final Game dameDePiqueGame;

    public DameDePiqueNetworkEngine(Deck deck, HostFacade hostFacade, Set<String> players, Game dameDePiqueGame) {
        super(deck, hostFacade);
        this.dameDePiqueGame = dameDePiqueGame;
    }

    public static void main(String[] args) {
        HostFacade hostFacade = Facade.getFacade();
        hostFacade.waitReady();

        hostFacade.createNewPlayer("Host");

        Game dameDePiqueGame = hostFacade.createNewGame("DAME_DE_PIQUE");

        hostFacade.waitForExtraPlayerCount(PLAYER_COUNT);

        DameDePiqueNetworkEngine host = new DameDePiqueNetworkEngine(new StandardDeck(), hostFacade, new HashSet<>(dameDePiqueGame.getPlayers()), dameDePiqueGame);
        host.play();
        System.exit(0);
    }

    
    @Override
    protected List<String> getInitialPlayers() {
        return new ArrayList<>(this.dameDePiqueGame.getPlayers());
    }

    @Override
    protected void playTurn(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'playTurn'");
    }

    @Override
    protected boolean isGameOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isGameOver'");
    }

    @Override
    protected void declareWinner(Player winner) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'declareWinner'");
    }
}
