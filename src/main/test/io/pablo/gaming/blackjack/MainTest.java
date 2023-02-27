package io.pablo.gaming.blackjack;

import io.pablo.gaming.cardgames.Card;
import io.pablo.gaming.cardgames.Deck;
import io.pablo.gaming.cardgames.Suits;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainTest extends TestCase {
    BlackjackDeck deck;
    public void setUp() throws Exception {
        super.setUp();
        deck = new BlackjackDeck();
        deck.shuffle();
    }

    public void tearDown() throws Exception {
    }

    public void testHouseTurn() {
        ArrayList<Card> hand = new ArrayList<>();
        setTestDeck(hand, 5,6);
        Deck house = new Deck();
        BlackjackDeck mockDeck = mock(BlackjackDeck.class);
        when(mockDeck.dealCard(2)).thenReturn(hand);
        when(mockDeck.dealCard()).thenReturn(new Card(Suits.SPADES, 5));
        when(mockDeck.dealCard()).thenReturn(new Card(Suits.SPADES, 5));

        int result = Main.houseTurn(mockDeck, house);
        assertEquals("No tiene ni idea", 21, result);
    }

    public void testGetHandValue() {
        ArrayList<Card> hand = new ArrayList<>();
        setTestDeck(hand, 2,3);

        int result = Main.getHandValue(hand);
        assertEquals("No coincide suma normal", 5, result);

        setTestDeck(hand, 1,1);
        result = Main.getHandValue(hand);
        assertEquals("No resta valor a los ases", 12, result);

        setTestDeck(hand, 11,12);
        result = Main.getHandValue(hand);
        assertEquals("No suma bien las figuras", 20, result);

        setTestDeck(hand, 11,7);
        result = Main.getHandValue(hand);
        assertEquals("No suma bien figuras y numeros", 17, result);

        setTestDeck(hand, 11,1);
        result = Main.getHandValue(hand);
        assertEquals("No suma bien el bjack", 21, result);



    }

    private void setTestDeck(ArrayList<Card> cards, int num1, int num2) {
        cards.clear();
        cards.add(new Card(Suits.CLUBS, num1));
        cards.add(new Card(Suits.CLUBS, num2));
    }
}