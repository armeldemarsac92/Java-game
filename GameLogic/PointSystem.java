package GameLogic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import PixelMap.GamePanel;

public class PointSystem {

    /*---------- Attributes ---------- */

    private static int coins = 0;
    private static int score = 0;
    private static JLabel scoreLabel;
    private static JLabel coinsLabel;
    

    /*---------- Getters ---------- */

    public static int getCoins(){
        return PointSystem.coins;
    }

    public static int getScore(){
        return PointSystem.score;
    }


    /*---------- Setters ---------- */

    public static void setCoins(int coins){
        PointSystem.coins = coins;
    }

    public static void setScore(int score){
        PointSystem.score = score;
    }


    /*---------- Methods ---------- */

    public static void earnCoins(int coins){
        PointSystem.coins += coins;
    }

    public static void incrementScore(int points){
        PointSystem.score += points;
    }

    public static void spendCoins(int coins){
        int coinsTemp = PointSystem.coins;
        if(coinsTemp - coins <= 0){
            coinsTemp = 0;
        }
        else{
            coinsTemp -= coins;
        }

        PointSystem.coins = coinsTemp;
    }

    public static void intializeScoreSystem(GamePanel gamePanel, Dimension screenSize){
        PointSystem.scoreLabel = new JLabel("Score: " + PointSystem.score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setVerticalAlignment(JLabel.TOP);
        scoreLabel.setBounds(20, 20, screenSize.width, screenSize.height);
        scoreLabel.setForeground(Color.CYAN);
        gamePanel.add(scoreLabel);
        gamePanel.setComponentZOrder(scoreLabel, 0);
    }

    public static void initializeCoinsSystem(GamePanel gamePanel, Dimension screenSize){
        PointSystem.coinsLabel = new JLabel("Coins :" + PointSystem.coins);
        coinsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        coinsLabel.setHorizontalAlignment(JLabel.RIGHT - (2 / 100 * screenSize.width));
        coinsLabel.setVerticalAlignment(JLabel.TOP - (20 / 100 * screenSize.height));
        coinsLabel.setBounds(-20, 20, screenSize.width, screenSize.height);
        coinsLabel.setForeground(Color.ORANGE);
        gamePanel.add(coinsLabel);
        gamePanel.setComponentZOrder(coinsLabel, 0);
    }

    public static void updateScoreLabel() {
        PointSystem.scoreLabel.setText("Score: " + PointSystem.score);
    }

    public static void updateCoinsLabel(){
        PointSystem.coinsLabel.setText("Coins :" + PointSystem.score);
    }
}
