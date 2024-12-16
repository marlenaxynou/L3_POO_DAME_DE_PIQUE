package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.List;

public class RandomDeck extends Deck {

    @Override
    public List<Card[]> dealCards(int numberOfPlayers) {
        int cardsPerPlayer = cards.size() / numberOfPlayers;
        List<Card[]> hands = new ArrayList<>();

        for (int i = 0; i < numberOfPlayers; i++) {
            hands.add(cards.subList(i * cardsPerPlayer, (i + 1) * cardsPerPlayer).toArray(new Card[0]));
        }
        return hands;
    }
}
