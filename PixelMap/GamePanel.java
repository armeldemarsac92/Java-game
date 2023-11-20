package PixelMap;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import GameLogic.*;

public class GamePanel extends JPanel {
    

    private JLabel backgroundLabel;
    private JLabel pauseLabel;

    private JPopupMenu pauseMenu;

    private boolean isPauseMenuOpen = false;
    private boolean isHowToPlayPopupOpen = false;

    public GamePanel(Dimension screenSize, Game game) {
        setPreferredSize(screenSize); // Set the panel size to the screen size
        setLayout(null); // Continue using null layout for absolute positioning

        // Initialize units and towers as before
        // Initialize background first
        initializeBackground(screenSize);
        // Then initialize pause icon
        initializePauseIcon(screenSize, game);
        // Initialize units
        initializeUnits();

        setComponentZOrder(pauseLabel, 0);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isPauseMenuOpen) {
                    game.resumeGame();
                    isPauseMenuOpen = false;
                }
            }
        });
    }

    private void initializeBackground(Dimension screenSize) {
        try {
            // Load the original image
            Image originalImage = ImageIO.read(new File("assets/background1.png"));

            // Scale the image to fit the screen size
            Image scaledImage = originalImage.getScaledInstance(screenSize.width, screenSize.height,
                    Image.SCALE_SMOOTH);

            // Set the scaled image as the icon of the JLabel
            ImageIcon backgroundImageIcon = new ImageIcon(scaledImage);
            backgroundLabel = new JLabel(backgroundImageIcon);
            backgroundLabel.setBounds(0, 0, screenSize.width, screenSize.height);
            add(backgroundLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializePauseIcon(Dimension screenSize, Game game) {
        try {
            Image pauseImage = ImageIO.read(new File("assets/bouton-pause.png"));
            Image scaledPauseImage = pauseImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            pauseLabel = new JLabel(new ImageIcon(scaledPauseImage));

            pauseLabel.setBounds(22, 22, 50, 50);

            add(pauseLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        createPauseMenu(game);

        pauseLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                game.pauseGame();

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - pauseMenu.getPreferredSize().width) / 2;
                int y = (screenSize.height - pauseMenu.getPreferredSize().height) / 2;

                pauseMenu.show(e.getComponent(), x, y);
            }
        });

    }

    private void createPauseMenu(Game game) {
        pauseMenu = new JPopupMenu("Pause Menu");
        Font menuFont = new Font("Arial", Font.BOLD, 16);

        String[] menuItems = { "Continue", "Restart", "Menu", "How to play ?" };
        for (String itemText : menuItems) {
            JMenuItem menuItem = new JMenuItem();
            menuItem.setLayout(new BorderLayout());

            JLabel label = new JLabel(itemText, SwingConstants.CENTER);
            label.setFont(menuFont);
            menuItem.add(label, BorderLayout.CENTER);

            Dimension menuItemSize = new Dimension(350, 80);
            menuItem.setPreferredSize(menuItemSize);

            menuItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    menuItem.setCursor(Cursor.getDefaultCursor());
                }
            });

            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (itemText.equals("Menu")) {
                        // Close the current game window
                        SwingUtilities.getWindowAncestor(GamePanel.this).dispose();
            
                        // Create and show the main menu
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                GameMenu menu = new GameMenu();
                                menu.setVisible(true);
                            }
                        });
                    }
                }
            });

            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (itemText.equals("How to play ?")) {
                        isHowToPlayPopupOpen = true;
                        JOptionPane.showMessageDialog(GamePanel.this,
                            "MusaReign is an engaging tower defense game where the objective is straightforward:\n prevent enemies from crossing the entire map to avoid losing life points.\n To achieve this, players must strategically construct defense towers using coins. Each successful\n defense not only halts enemy progress but also enhances the player's ability to fortify their\n defenses further. The game combines strategic planning with quick decision-making, offering\n an immersive experience for all tower defense enthusiasts.",
                            "How to Play", JOptionPane.INFORMATION_MESSAGE);
                        isHowToPlayPopupOpen = false;
                        checkAndResumeGame(game);
                    }
                }
            });
            

            pauseMenu.add(menuItem);
        }

        
        

        pauseMenu.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                isPauseMenuOpen = true;
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(() -> {
                    checkAndResumeGame(game);
                });
                isPauseMenuOpen = false;
            }

            public void popupMenuCanceled(PopupMenuEvent e) {
                if (!isHowToPlayPopupOpen) {
                    game.resumeGame();
                }
        
                isPauseMenuOpen = false;
            }
        });

    }

    private void checkAndResumeGame(Game game) {
        if (!isPauseMenuOpen && !isHowToPlayPopupOpen) {
            game.resumeGame();
        }
    }

    private void initializeUnits() {
        new ArcherTower(new Coordinates(350, 250), GamePanel.this);
        new ArcherTower(new Coordinates(850, 250), GamePanel.this);
        new ArcherTower(new Coordinates(1350, 250), GamePanel.this);
        new ArcherTower(new Coordinates(1850, 750), GamePanel.this);
        new ArcherTower(new Coordinates(550, 750), GamePanel.this);

    }

    

    public GamePanel getInstance() {
        return this;
    }
}

