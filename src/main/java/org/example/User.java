package org.example;

public class User {
    private String[][] userCard = new String[7][2];
    private boolean isBusted = false;
    private boolean isA = false;
    private int score = 0;

    public User () {
        for (int i=0; i<5; i++) {
            userCard[i][0] = null;
        }
    }
    public void setSuit(String suit, int index) {
        this.userCard[index][0] = suit;
    }
    public void setRank(String rank, int index) {
        this.userCard[index][1] = rank;
    }
    public void setBusted(boolean isBusted) {
        this.isBusted = isBusted;
    }
    public void setA(boolean isA) { this.isA = isA; }
    public void setScore(int score) { this.score = score; }
    public String getSuit(int index) {
        return userCard[index][0];
    }
    public String getRank(int index) {
        return userCard[index][1];
    }
    public boolean getBusted() { return isBusted; }
    public boolean getA() { return isA; }
    public int getScore() { return score; }
}