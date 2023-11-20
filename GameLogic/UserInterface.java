package GameLogic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import PixelMap.GamePanel;

public class UserInterface {

    /*---------- Attributes ---------- */

    private static int coins = 0;
    private static int score = 0;
    private static JLabel scoreLabel;
    private static JLabel coinsLabel;
    private static JLabel userNameLabel;
    public static Map<String, Integer> scores = new HashMap<>();


    /*---------- Getters ---------- */

    public static int getCoins(){
        return UserInterface.coins;
    }

    public static int getScore(){
        return UserInterface.score;
    }

    public static int getUserScore(String name) {
        return UserInterface.scores.getOrDefault(name, 0);
    }


    /*---------- Setters ---------- */

    public static void setCoins(int coins){
        UserInterface.coins = coins;
    }

    public static void setScore(int score){
        UserInterface.score = score;
    }


    /*---------- Methods ---------- */

    public static void earnCoins(int coins){
        UserInterface.coins += coins;
    }

    public static void incrementScore(int points){
        UserInterface.score += points;
    }

    public static void spendCoins(int coins){
        int coinsTemp = UserInterface.coins;
        if(coinsTemp - coins <= 0){
            coinsTemp = 0;
        }
        else{
            coinsTemp -= coins;
        }

        UserInterface.coins = coinsTemp;
    }

    public static boolean hasEnoughCoins(int price){
        if(UserInterface.coins - price < 0){
            return false;
        }
        else{
            return true;
        }
    }

    public static void intializeScoreSystem(GamePanel gamePanel, Dimension screenSize){
        UserInterface.scoreLabel = new JLabel("Score: " + UserInterface.score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setVerticalAlignment(JLabel.TOP);
        scoreLabel.setBounds(20, 20, screenSize.width, screenSize.height);
        scoreLabel.setForeground(Color.CYAN);
        gamePanel.add(scoreLabel);
        gamePanel.setComponentZOrder(scoreLabel, 0);
    }

    public static void initializeCoinsSystem(GamePanel gamePanel, Dimension screenSize){
        UserInterface.coinsLabel = new JLabel("Coins :" + UserInterface.coins);
        coinsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        coinsLabel.setHorizontalAlignment(JLabel.RIGHT - (2 / 100 * screenSize.width));
        coinsLabel.setVerticalAlignment(JLabel.TOP - (20 / 100 * screenSize.height));
        coinsLabel.setBounds(-20, 20, screenSize.width, screenSize.height);
        coinsLabel.setForeground(Color.ORANGE);
        gamePanel.add(coinsLabel);
        gamePanel.setComponentZOrder(coinsLabel, 0);
    }

    public static void initializeUserName(GamePanel gamePanel, Dimension screenSize, String username){
        UserInterface.userNameLabel = new JLabel("Username: " + username);
        System.out.println("Name at initialization: " + username);
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        userNameLabel.setHorizontalAlignment(JLabel.CENTER);
        userNameLabel.setVerticalAlignment(JLabel.BOTTOM);
        userNameLabel.setBounds(1, 1, screenSize.width, screenSize.height);
        userNameLabel.setForeground(Color.GREEN);
        gamePanel.add(userNameLabel);
        gamePanel.setComponentZOrder(userNameLabel, 0);
    }

    public static void updateScoreLabel() {
        UserInterface.scoreLabel.setText("Score: " + UserInterface.score);
    }

    public static void updateCoinsLabel(){
        UserInterface.coinsLabel.setText("Coins :" + UserInterface.coins);
    }

    public static void addScore(String name, int score) {
        UserInterface.scores.put(name, score);
    }

    public static void removeScore(String name) {
        UserInterface.scores.remove(name);
    }

    public static void printScores() {
        System.out.println("Scores:");
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
