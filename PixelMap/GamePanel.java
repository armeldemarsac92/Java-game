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
import java.util.TimerTask;
import java.util.Timer;
import java.util.Random;
import GameLogic.*;

public class GamePanel extends JPanel {
    private Game game;
    private Timer waveTimer;
    private int waveInterval = 30000; // 30 seconds between waves
    private int currentWave;
    private int hordeSize = 4;
    private int threashold = 10;
    private int maxTanker = 2;
    private int mobSpeed = 1;

    private JLabel backgroundLabel;
    private JLabel pauseLabel;

    private JPopupMenu pauseMenu;

    private boolean isPauseMenuOpen = false;
    private boolean isHowToPlayPopupOpen = false;


    public GamePanel(Dimension screenSize, Game game) {
        this.game = game;
        setPreferredSize(screenSize); // Set the panel size to the screen size
        setLayout(null); // Continue using null layout for absolute positioning

        // Initialize units and towers as before
        // Initialize background first
        initializeBackground(screenSize);
        // Then initialize pause icon
        initializePauseIcon(screenSize);
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

    private void initializePauseIcon(Dimension screenSize) {
        try {
            Image pauseImage = ImageIO.read(new File("assets/bouton-pause.png"));
            Image scaledPauseImage = pauseImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            pauseLabel = new JLabel(new ImageIcon(scaledPauseImage));

            pauseLabel.setBounds(22, 22, 50, 50);

            add(pauseLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        createPauseMenu();

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

    private void createPauseMenu() {
        pauseMenu = new JPopupMenu("Pause Menu");
        Font menuFont = new Font("Arial", Font.BOLD, 16);

        String[] menuItems = { "Continue", "How to play ?"};
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
                    if (itemText.equals("How to play ?")) {
                        isHowToPlayPopupOpen = true;
                        JOptionPane.showMessageDialog(GamePanel.this,
                            "MusaReign is an engaging tower defense game where the objective is straightforward:\n prevent enemies from crossing the entire map to avoid losing life points.\n To achieve this, players must strategically construct defense towers using coins. Each successful\n defense not only halts enemy progress but also enhances the player's ability to fortify their\n defenses further. The game combines strategic planning with quick decision-making, offering\n an immersive experience for all tower defense enthusiasts.",
                            "How to Play", JOptionPane.INFORMATION_MESSAGE);
                        isHowToPlayPopupOpen = false;
                        checkAndResumeGame();
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
                    checkAndResumeGame();
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

    private void checkAndResumeGame() {
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

        waveTimer = new Timer();
        waveTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                spawnWave();
            }
        }, 0, waveInterval);
    }

    private void spawnWave() {
        currentWave++;
        hordeSize++;
        waveInterval += 10;
        System.out.println("Spawning wave: " + currentWave);
        Random random = new Random();
        int minTimer = 800;
        int maxTimer = 2000;
        int randomTimer = minTimer + random.nextInt(maxTimer - minTimer + 1);

        new Timer().schedule(new TimerTask() {
            private int count = 0;
            int tankerCount = 0;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int standardY = (int) (screenSize.getHeight() / 2) - 100;

            @Override
            public void run() {
                if (count < hordeSize) {
                    int minY = standardY - 50; // Minimum Y-coordinate
                    int maxY = standardY + 50; // Maximum Y-coordinate
                    int randomY = minY + random.nextInt(maxY - minY + 1); // Generate a random Y-coordinate within the
                                                                          // range
                    int minChance = 0;
                    int maxChance = 10;
                    int tankerApparitionChance = random.nextInt(maxChance - minChance + 1);

                    Barbarian barbarian = new Barbarian(new Coordinates(-400, randomY), GamePanel.this);
                    barbarian.setSpeed(mobSpeed);
                    count++;

                    if (tankerApparitionChance >= threashold && tankerCount <= maxTanker) {
                        new Tanker(new Coordinates(-400, randomY), GamePanel.this);
                        count += 2;
                        tankerCount++;
                    }

                } else {
                    this.cancel();
                }
            }
        }, 0, randomTimer); // random delay between each mob spawn
        threashold--;
        if (currentWave % 5 == 0) {
            mobSpeed++;
            maxTanker++;
        }
    }

    public GamePanel getInstance() {
        return this;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tower Defense Game");
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            Game game = new Game();
            GamePanel panel = new GamePanel(screenSize, game);

            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize frame before making it visible
            frame.setVisible(true);
        });
    }
}