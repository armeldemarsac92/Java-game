package PixelMap;

import javax.swing.*;

import GameLogic.User;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class GameMenu extends JFrame {

    private JTextField usernameField;

    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String fileName) {
            URL imageUrl = getClass().getResource(fileName);
            if (imageUrl != null) {
                backgroundImage = new ImageIcon(imageUrl).getImage();
            } else {
                System.err.println("File not found: " + fileName);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
        }
    }

    public GameMenu() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);

        BackgroundPanel backgroundPanel = new BackgroundPanel("assets/menu-bg.jpg");
        this.setContentPane(backgroundPanel);
        backgroundPanel.setLayout(null);

        // Username input field
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Serif", Font.PLAIN, 20));
        int textFieldX = (screenSize.width - 200) / 2;
        int textFieldY = (int) (screenSize.height * 0.7) - 50;
        usernameField.setBounds(textFieldX, textFieldY, 200, 50);
        backgroundPanel.add(usernameField);

        // Start button
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Serif", Font.BOLD, 24));
        startButton.setForeground(Color.decode("#463c32"));
        startButton.setBackground(Color.decode("#79AD9A"));
        startButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#DECBA4"), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        startButton.setFocusPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setOpaque(true);
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                startButton.setBackground(Color.decode("#9DBF9E"));
                startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent evt) {
                startButton.setBackground(Color.decode("#79AD9A"));
                startButton.setCursor(Cursor.getDefaultCursor());
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                if (!username.isEmpty()) {
                    new User(username);
                    setVisible(false);
                    dispose();

                    // Start the game with the entered username
                    System.setProperty("sun.java2d.opengl", "True");
                    Game game = new Game(username);
                    game.frame.setVisible(true);
                    new Thread(game).start();
                } else {
                    // Handle the case where username is not entered
                    JOptionPane.showMessageDialog(GameMenu.this,
                        "Please enter a username to start the game.",
                        "Username Required",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        int buttonWidth = 200;
        int buttonHeight = 50;
        int x = (screenSize.width - buttonWidth) / 2;
        int y = (int) (screenSize.height * 0.8) - buttonHeight;
        startButton.setBounds(x, y, buttonWidth, buttonHeight);
        backgroundPanel.add(startButton);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameMenu menu = new GameMenu();
            menu.setVisible(true);
        });
    }
}
