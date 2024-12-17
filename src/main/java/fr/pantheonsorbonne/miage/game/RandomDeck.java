package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.pantheonsorbonne.miage.exception.NoMoreCardException;

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

    @Override
    public Card drawCard() throws NoMoreCardException {
        if (cards.isEmpty()) {
            throw new NoMoreCardException();
        }
        Random random = new Random();
        return cards.remove(random.nextInt(cards.size()));
    }
}
