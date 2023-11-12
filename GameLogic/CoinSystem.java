package GameLogic;

public class CoinSystem {
    private static int points = 0;

    
    public static int getCoins(){
        return CoinSystem.points;
    }

    public static void setCoins(int points){
        CoinSystem.points = points;
    }

    public static void earnCoins(int points){
        CoinSystem.points += points;
    }
}
