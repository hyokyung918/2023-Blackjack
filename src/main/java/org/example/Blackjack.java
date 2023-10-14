package org.example;

import java.util.Random;
import java.util.Scanner;

public class Blackjack {
    public static void blackjack() {
        User Player = new User();
        User Dealer = new User();
        boolean isEnd = false;

        Card[][] cardArr = new Card[4][13];
        resetCard(cardArr);

        for (int i=0; i<2; i++) {
            selectCard(cardArr, Player, i);
            selectCard(cardArr, Dealer, i);
        }

        int playerIndex = 2;
        while (true) {
            printScreen(Player, Dealer, isEnd);
            isEnd = ifContinue();

            Player.setBusted(ifBusted(Player));
            if (Player.getBusted()) {
                isEnd = true;
                break;
            }
            else if (isEnd)
                break;
            selectCard(cardArr, Player, playerIndex);
            playerIndex++;
        }

        if(!Player.getBusted()) {
            dealerHit(cardArr, Dealer);
        }

        winOrLose(Dealer, Player);
        printScreen(Player, Dealer, isEnd);

    }
    public static void resetCard(Card[][] cardArr) {
        for (int i=0; i<4; i++) {
            for (int j=0; j<13; j++) {
                cardArr[i][j] = new Card();

                if (i==0)
                    cardArr[i][j].setSuit("♠");
                else if (i==1)
                    cardArr[i][j].setSuit("♣");
                else if (i==2)
                    cardArr[i][j].setSuit("◆");
                else
                    cardArr[i][j].setSuit("♥");

                if (j==0)
                    cardArr[i][j].setRank("A");
                else if (j<10)
                    cardArr[i][j].setRank(String.valueOf(j+1));
                else if (j==10)
                    cardArr[i][j].setRank("J");
                else if (j==11)
                    cardArr[i][j].setRank("Q");
                else

                    cardArr[i][j].setRank("K");
            }
        }
    }

    public static void selectCard(Card[][] cardArr, User ownCard, int i) {
        Random rand = new Random();
        int suit, rank;
        while (true) {
            suit = rand.nextInt(4);
            rank = rand.nextInt(13);
            if (cardArr[suit][rank].getRank() != null)
                break;
        }
        ownCard.setSuit(cardArr[suit][rank].getSuit(), i);
        ownCard.setRank(cardArr[suit][rank].getRank(), i);
        int tempScore = countScore(ownCard.getRank(i), ownCard);
        ownCard.setScore(ownCard.getScore() + tempScore);
        cardArr[suit][rank].setRank(null);
    }

    public static void printScreen(User Player, User Dealer, boolean isEnd) {
        System.out.println("\n---------------- BlackJack Game -----------------");
        printDealerCard(Dealer, isEnd);
        printPlayerCard(Player);
        System.out.println("\n-------------------------------------------------");
    }

    public static void printDealerCard(User Dealer, boolean isEnd) {
        System.out.print("   #Dealer : ");
        System.out.print(Dealer.getRank(0) + "(" + Dealer.getSuit(0) + ") ");

        if (isEnd) {
            int i=1;
            while (Dealer.getRank(i) != null) {
                System.out.print(Dealer.getRank(i) + "(" + Dealer.getSuit(i) + ") ");
                i++;
            }
            System.out.println();
        }
        else System.out.println("XX");
    }

    public static void printPlayerCard(User Player) {
        System.out.print("   #Player : ");
        int i=0;
        while (Player.getRank(i) != null) {
            System.out.print(Player.getRank(i) + "(" + Player.getSuit(i) + ") ");
            i++;
        }
    }

    public static boolean ifContinue() {
        Scanner scan = new Scanner(System.in);

        char hitOrStand;
        while (true) {
            System.out.print("Hit or Stand? (H/S): ");
            hitOrStand = scan.next().charAt(0);
            if (hitOrStand == 's' || hitOrStand == 'S')
                return true;
            else if (hitOrStand == 'h' || hitOrStand == 'H')
                return false;
            System.out.println("잘못 입력하셨습니다");
        }
    }

    public static boolean ifBusted(User user) {
        if (user.getScore() > 21 && user.getA())
            user.setScore(user.getScore() - 10);
        if (user.getScore() > 21)
            return true;
        return false;
    }

    public static int countScore(String rank, User user) {
        switch (rank) {
            case "A":
                user.setA(true);
                return 11;
            case "J":
            case "Q":
            case "K":
                return 10;
            default: return Integer.valueOf(rank);
        }
    }

    public static void dealerHit(Card[][] cardArr, User Dealer) {
        int i=2;
        while (Dealer.getScore() < 17 && !Dealer.getBusted())  {
            selectCard(cardArr, Dealer, i);
            Dealer.setBusted(ifBusted(Dealer));
            i++;
        }
    }

    public static void winOrLose(User Dealer, User Player) {
        if (Dealer.getBusted())
            System.out.println("\nDealer busted...");
        else if (Player.getBusted())
            System.out.println("\nPlayer busted...");
        else if (Dealer.getScore() == Player.getScore())
            System.out.println("\nEqual points...");
        else {
            int dealerPoint = 21 - Dealer.getScore();
            int playerPoint = 21 - Player.getScore();
            if (dealerPoint < playerPoint)
                System.out.println("\nDealer Wins...");
            else
                System.out.println("\nPlayer Wins...");
        }
    }
}
