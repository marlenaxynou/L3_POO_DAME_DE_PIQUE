package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.engine.local.LocalDameDePiqueGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PassCardsTest {

    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private List<Player> players;
    private LocalDameDePiqueGame game;

    @BeforeEach
    void setUp() {
        player1 = new Player("Joueur1");
        player2 = new Player("Joueur2");
        player3 = new Player("Joueur3");
        player4 = new Player("Joueur4");
        players = Arrays.asList(player1, player2, player3, player4);
        game = new LocalDameDePiqueGame(new DeterministDeck(Card.getAllPossibleCards()), players);
    }

    @Test
    void testPassCardsToLeft() {
        game.setRound(1);
        player1.setHand(createHand("2H", "3H", "4H"));
        player2.setHand(createHand("5H", "6H", "7H"));
        player3.setHand(createHand("8H", "9H", "10H"));
        player4.setHand(createHand("JH", "QH", "KH"));

        game.exchangeCards();

        assertTrue(player2.getHand().containsAll(createHand("2H", "3H", "4H")));
        assertTrue(player3.getHand().containsAll(createHand("5H", "6H", "7H")));
        assertTrue(player4.getHand().containsAll(createHand("8H", "9H", "10H")));
        assertTrue(player1.getHand().containsAll(createHand("JH", "QH", "KH")));
    }

    @Test
    void testPassCardsToRight() {
        game.setRound(2);
        player1.setHand(createHand("2H", "3H", "4H"));
        player2.setHand(createHand("5H", "6H", "7H"));
        player3.setHand(createHand("8H", "9H", "10H"));
        player4.setHand(createHand("JH", "QH", "KH"));

        game.exchangeCards();

        assertTrue(player4.getHand().containsAll(createHand("2H", "3H", "4H")));
        assertTrue(player1.getHand().containsAll(createHand("5H", "6H", "7H")));
        assertTrue(player2.getHand().containsAll(createHand("8H", "9H", "10H")));
        assertTrue(player3.getHand().containsAll(createHand("JH", "QH", "KH")));
    }

    @Test
    void testPassCardsAcross() {
        game.setRound(3);
        player1.setHand(createHand("2H", "3H", "4H"));
        player2.setHand(createHand("5H", "6H", "7H"));
        player3.setHand(createHand("8H", "9H", "10H"));
        player4.setHand(createHand("JH", "QH", "KH"));

        game.exchangeCards();

        assertTrue(player3.getHand().containsAll(createHand("2H", "3H", "4H")));
        assertTrue(player4.getHand().containsAll(createHand("5H", "6H", "7H")));
        assertTrue(player1.getHand().containsAll(createHand("8H", "9H", "10H")));
        assertTrue(player2.getHand().containsAll(createHand("JH", "QH", "KH")));
    }

    @Test
    void testNoPassCards() {
        game.setRound(4);
        player1.setHand(createHand("2H", "3H", "4H"));
        player2.setHand(createHand("5H", "6H", "7H"));
        player3.setHand(createHand("8H", "9H", "10H"));
        player4.setHand(createHand("JH", "QH", "KH"));

        game.exchangeCards();

        assertTrue(player1.getHand().containsAll(createHand("2H", "3H", "4H")));
        assertTrue(player2.getHand().containsAll(createHand("5H", "6H", "7H")));
        assertTrue(player3.getHand().containsAll(createHand("8H", "9H", "10H")));
        assertTrue(player4.getHand().containsAll(createHand("JH", "QH", "KH")));
    }

    private List<Card> createHand(String... cardStrings) {
        List<Card> hand = new ArrayList<>();
        for (String cardString : cardStrings) {
            hand.add(Card.valueOf(cardString));
        }
        return hand;
    }
}