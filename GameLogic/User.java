package GameLogic;

import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class User extends JDialog {
    private JTextField name;
    private static int userHp = 500;

    public User(){
        super(new JFrame("Enter your username"), "Add Member");

        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setMinimumSize(new Dimension(500, 500));
        this.name = new JTextField();




        JButton inputButton = new JButton("Submit");
        inputButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Name entered :" + getName());
                close();
            }});

        this.setLayout(new GridLayout(3, 1, 5, 5));

        this.add(name);
        this.add(inputButton);
        this.pack();
    }

    private void close(){
        this.dispose();
    }

    public String getName(){
        return this.name.getText();
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

    
