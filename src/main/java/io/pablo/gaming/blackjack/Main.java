package io.pablo.gaming.blackjack;

import io.pablo.gaming.cardgames.Card;
import io.pablo.gaming.cardgames.Deck;

import java.util.ArrayList;
import java.util.Scanner;
//TODO: Upload to github
public class Main {

    static Scanner sc = new Scanner(System.in);

    static boolean gameOver = false;

    static int money = 1000;

    public static void main(String[] args) {
        BlackjackDeck deck = new BlackjackDeck();
        Deck player = new Deck();
        Deck house = new Deck();
        deck.shuffle();
        System.out.println("Welcome to Pablo's Casino, you start with "+money+" pesos. Good luck :)");
        while (!gameOver) {
            turn(deck, player, house);
        }
        System.out.println("BYEEEE! Come back soon to Pablo's Casino");
    }

    public static void turn(BlackjackDeck deck, Deck player, Deck house) {
        System.out.println("-------------------\n-------------------");
        int bet = getBet();
        if(!gameOver) {
            System.out.println("You betted "+ bet + " pesitos");
            bet *= 2;
            int playerScore = playerTurn(deck, player);
            int houseScore = houseTurn(deck, house);
            showBoard(player);
            showHouse(house);

            System.out.println("Player Score: "+playerScore+". House Score: "+houseScore);

            if(playerScore > 21) {
                System.out.println("Busted! House wins.");
            } else if(houseScore > 21) {
                money += bet;
                System.out.println("House busted! Player wins. +" + bet + "$. Current money: "+money+"$");
            } else if(playerScore == 21) {
                money += bet;
                System.out.println("Blackjack!! Player wins. +" + bet + "$. Current money: "+money+"$");
            } else if(playerScore > houseScore) {
                money += bet;
                System.out.println("Player wins. +" + bet + "$. Current money: "+money+"$");
            } else {
                System.out.println("House wins.");
            }
            deck.addToDiscard((ArrayList<Card>) house.dealCard(house.getCards().size()));
            deck.addToDiscard((ArrayList<Card>) player.dealCard(player.getCards().size()));
        }
    }

    public static int houseTurn(BlackjackDeck deck, Deck house) {
        house.setCards((ArrayList<Card>) deck.dealCard(2));
        while(getHandValue(house.getCards()) < 17) {
            house.addCard(deck.dealCard());
        }
        return getHandValue(house.getCards());
    }

    public static int getHandValue(ArrayList<Card> cards) {
        boolean ace = false;
        int handValue = 0;
        for(Card c : cards) {
            if(c.getNumber() == 1) ace = true;
        }
        for(Card c : cards) {
            if(c.getNumber() == 1) {
                handValue += 11;
            } else if(c.getNumber() == 11 || c.getNumber() == 12 || c.getNumber() == 13) {
                handValue += 10;
            } else {
                handValue += c.getNumber();
            }
        }
        if(handValue > 21 && ace) {
            handValue -= 10;
        }
        return handValue;
    }

    public static int playerTurn(BlackjackDeck deck, Deck player) {
        player.setCards((ArrayList<Card>) deck.dealCard(2));
        int value = getHandValue(player.getCards());
        boolean done = false;
        while (!done) {
            showBoard(player);
            String cmd = getCommand();
            if (cmd.equals("hit")) {
                player.addCard(deck.dealCard());
                value = getHandValue(player.getCards());
                if(value > 20) {
                    done = true;
                }
            } else if (cmd.equals("stay")) {
                done = true;
            } else if (cmd.equals("quit")) {
                done = true;
                gameOver = true;
            }
        }
        return value;
    }

    public static String getCommand() {
        boolean gotten = false;
        String cmd = "";
        while(!gotten) {
            System.out.print("What do you want to do? ");
            cmd = sc.next();
            try {
                validateCommand(cmd);
                gotten = true;
            } catch (Exception e) {
                System.out.println("Command not supported: "+ cmd);
            }
        }
        return cmd;
    }

    private static String validateCommand(String command) throws Exception {
        String validCommands = "hit|stay|quit";
        if (!validCommands.contains(command)) {
            throw new Exception("Command not supported: " + command);
        }
        return command;
    }

    public static int getBet() {
        boolean gotten = false;
        int bet = 0;
        while (!gotten && !gameOver) {
            bet = getNumber();
            if(gameOver) {

            }else if(money < bet) {
                System.out.println("Not enough money");
            } else if(bet < 1) {
                System.out.println("You didn't bet enough money");
            } else {
                gotten = true;
            }
        }
        money -= bet;
        return bet;
    }

    private static int getNumber() {
        boolean gotten = false;
        int value = 0;
        while (!gotten) {
                System.out.print("How much do you want to bet? ");
                String frase = sc.nextLine();
                if(frase.equals("quit")) {
                    gotten = true;
                    gameOver = true;
                } else {
                    try {
                        value = Integer.parseInt(frase);
                        gotten = true;
                    } catch (Exception e) {
                        System.out.println("Not a number.");
                    }
                }
                
        }
        return value;
    }

    public static void showBoard(Deck player) {
        System.out.println("Your money: " + money + "$");
        System.out.println("Your cards: ");
        System.out.print("  |  ");
        for(Card c : player.getCards()) {
            System.out.print(c.getValue(true)+"  |  ");
        }
        System.out.print("\n");
    }

    public static void showHouse(Deck house) {
        System.out.println("House cards: ");
        System.out.print("  |  ");
        for(Card c : house.getCards()) {
            System.out.print(c.getValue(true)+"  |  ");
        }
        System.out.print("\n");
    }
}
