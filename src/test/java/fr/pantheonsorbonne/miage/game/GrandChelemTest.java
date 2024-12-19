package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.engine.local.LocalDameDePiqueGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GrandChelemTest {

    private Player player;
    private List<Player> players;
    private LocalDameDePiqueGame game;

    @BeforeEach
    void setUp() {
        player = new Player("Joueur1");
        players = Arrays.asList(
                player,
                new Player("Joueur2"),
                new Player("Joueur3"),
                new Player("Joueur4")
        );
        game = new LocalDameDePiqueGame(new DeterministDeck(Card.getAllPossibleCards()), players);
    }

    @Test
    void testHasGrandChelem() {
        List<Card> grandChelemHand = new ArrayList<>();
        for (CardValue value : CardValue.values()) {
            if (value != CardValue.JOKER) {
                grandChelemHand.add(new Card(CardColor.HEARTS, value));
            }
        }
        grandChelemHand.add(new Card(CardColor.SPADES, CardValue.QUEEN));

        player.setHand(grandChelemHand);

        assertTrue(player.hasGrandChelem());
    }

    @Test
    void testGrandChelemScoreUpdate() {
        List<Card> grandChelemHand = new ArrayList<>();
        for (CardValue value : CardValue.values()) {
            if (value != CardValue.JOKER) {
                grandChelemHand.add(new Card(CardColor.HEARTS, value));
            }
        }
        grandChelemHand.add(new Card(CardColor.SPADES, CardValue.QUEEN));

        player.setHand(grandChelemHand);

        game.checkGrandChelem();

        assertEquals(0, player.getScore());
        for (Player otherPlayer : players) {
            if (otherPlayer != player) {
                assertEquals(26, otherPlayer.getScore());
            }
        }
    }
}