package GraphicInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/* DEMANDER SI OPTI D'AVOIR UN EPRIVATE CLASS A L'INTEREIEN DE LA CLASS GAMEMENU */

public class GameMenu extends JFrame {

    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String fileName) {
            URL imageUrl = getClass().getResource(fileName);

            if (imageUrl != null) {
                backgroundImage = new ImageIcon(imageUrl).getImage();
            } else {
                System.err.println("Le fichier n'a pas été trouvé : " + fileName);
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
        backgroundPanel.setLayout(new FlowLayout());

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Serif", Font.BOLD, 24));
        startButton.setForeground(Color.decode("#463c32"));
        startButton.setBackground(Color.decode("#79AD9A"));
        startButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode("#DECBA4"), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        startButton.setFocusPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setOpaque(true);

        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(Color.decode("#9DBF9E"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(Color.decode("#79AD9A"));
            }
        });

        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();

                JFrame mapFrame = new JFrame("Game Map");
                mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Map mapPanel = new Map();
                mapFrame.add(mapPanel);
                mapFrame.pack();
                mapFrame.setLocationRelativeTo(null);
                mapFrame.setVisible(true);
            }
        });
        
        backgroundPanel.setLayout(null);

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
