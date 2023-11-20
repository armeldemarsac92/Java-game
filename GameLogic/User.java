package GameLogic;

public class User {
    private String name;
    private static int userHp = 500;

    public User(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public static int getUserHp(){
        return User.userHp;
    }

    public static void decrementUserHp(int damage){
        if(User.userHp - damage >= 0){
            User.userHp -= damage;
        }
        else{
            User.userHp = 0;
        }
    }
}

    
