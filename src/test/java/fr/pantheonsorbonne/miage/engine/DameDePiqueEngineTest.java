package fr.pantheonsorbonne.miage.engine;

import fr.pantheonsorbonne.miage.engine.local.LocalDameDePiqueGame;
import fr.pantheonsorbonne.miage.exception.NoMoreCardException;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.DeterministDeck;
import fr.pantheonsorbonne.miage.game.Player;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DameDePiqueEngineTest {

    DameDePiqueGameEngine engine;
    List<Player> players;

    @BeforeEach
    void setUp() {
        players = Arrays.asList(
                new Player("Joueur1"),
                new Player("Joueur2"),
                new Player("Joueur3"),
                new Player("Joueur4")
        );
        this.engine = new LocalDameDePiqueGame(new DeterministDeck(Card.getAllPossibleCards()), players);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getInitialPlayers() {
        assertTrue(engine.getPlayers().containsAll(players));
    }

    @Test
    void giveCardsToPlayer() throws NoMoreCardException {
        Collection<Card> cards = Arrays.asList(Card.valueOf("KH"), Card.valueOf("2S"));
        Player player = players.get(0);
        player.setHand(new ArrayList<>(cards));
        Collection<Card> cardInHand = player.getHand();
        assertTrue(cards.containsAll(cardInHand));
        assertTrue(cardInHand.containsAll(cards));
    }

    @Test
    void playRoundSimpleRound() {
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        player1.setHand(Arrays.asList(Card.valueOf("KH")));
        player2.setHand(Arrays.asList(Card.valueOf("QH")));
        engine.playTurn(player1);
        engine.playTurn(player2);
        assertEquals(1, player1.getScore());
        assertEquals(1, player2.getScore());
    }

    @Test
    void playRoundEquality() {
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        player1.setHand(Arrays.asList(Card.valueOf("KH")));
        player2.setHand(Arrays.asList(Card.valueOf("KD")));
        engine.playTurn(player1);
        engine.playTurn(player2);
        assertEquals(0, player1.getScore());
        assertEquals(0, player2.getScore());
    }

    @Test
    void declareWinner() {
        Player winner = engine.determineWinner();
        assertNotNull(winner);
    }

    @Test
    void play() {
        engine.play();
        Player winner = engine.determineWinner();
        assertNotNull(winner);
    }
}