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

    public static void spendCoins(int points){
        int coinsTemp = CoinSystem.points;
        if(coinsTemp - points <= 0){
            coinsTemp = 0;
        }
        else{
            coinsTemp -= points;
        }

        CoinSystem.points = coinsTemp;
    }
}
