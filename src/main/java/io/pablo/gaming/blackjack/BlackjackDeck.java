package io.pablo.gaming.blackjack;

import io.pablo.gaming.cardgames.Card;
import io.pablo.gaming.cardgames.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlackjackDeck extends Deck {
    private ArrayList<Card> discards = new ArrayList<Card>();

    public ArrayList<Card> getDiscards() {
        return discards;
    }

    public void setDiscards(ArrayList<Card> discards) {
        this.discards = discards;
    }

    public void addToDiscard(ArrayList<Card> discards) {
        this.discards.addAll(discards);
    }

    public Card dealCard() {
        return dealCard(1).get(0);
    }
    public List<Card> dealCard(int cantCards) {
        List<Card> cards = new ArrayList<Card>();
        for(int i = 0; i < cantCards; i++) {
            try {
                Card card = this.getCards().remove(0);
                cards.add(card);
            } catch (Exception e) {
                restackDeck();
                shuffle();
                cards.add(dealCard());
            }
        }
        return cards;
    }

    public void restackDeck() {
        addCards(discards);
        discards.clear();
    }

    public BlackjackDeck() {
       defaultDeck();
   }

   public void defaultDeck() {
       this.prepareDefaultDeck();
       ArrayList<Card> temp = new ArrayList<Card>();
       temp = this.getCards();
       this.addCards(temp);
   }

}