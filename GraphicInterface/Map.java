package GraphicInterface;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Map extends JPanel {

    private static final int WIDTH = 33;
    private static final int HEIGHT = 19;
    private int boxWidth;
    private int boxHeight;

    private int[][] map = new int[HEIGHT][WIDTH];

    private BufferedImage mapImage;
    private BufferedImage pauseButtonImage;

    private JPopupMenu pauseMenu;
    

    public Map() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        boxWidth = screenSize.width / WIDTH;
        boxHeight = screenSize.height / HEIGHT;

        int remainingWidth = screenSize.width - (boxWidth * WIDTH);
        int remainingHeight = screenSize.height - (boxHeight * HEIGHT);

        boxWidth += remainingWidth / WIDTH;
        boxHeight += remainingHeight / HEIGHT;

        setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        generateGround();

        try {
            mapImage = ImageIO.read(getClass().getResourceAsStream("assets/map-bg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedImage originalPauseButton = ImageIO.read(getClass().getResourceAsStream("assets/bouton-pause.png"));
            pauseButtonImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = pauseButtonImage.createGraphics();
            g2d.drawImage(originalPauseButton.getScaledInstance(50, 50, Image.SCALE_SMOOTH), 0, 0, null);
            g2d.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }

        createPauseMenu();
        
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (isPauseButtonClicked(e.getX(), e.getY())) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isPauseButtonClicked(e.getX(), e.getY())) {
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    int screenWidth = screenSize.width;
                    int screenHeight = screenSize.height;

                    pauseMenu.pack();

                    int menuWidth = pauseMenu.getPreferredSize().width;
                    int menuHeight = pauseMenu.getPreferredSize().height;

                    int x = (screenWidth - menuWidth) / 2;
                    int y = (screenHeight - menuHeight) / 2;

                    pauseMenu.show(Map.this, x, y);
                }
            }
        });
    }

    private void generateGround() {
        int[][] predefinedMap = {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
                { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
                { 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 0 },
                { 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 0 },
                { 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 0 },
                { 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 0 },
                { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        };

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                map[i][j] = predefinedMap[i][j];
            }
        }
    }

    private void createPauseMenu() {
        pauseMenu = new JPopupMenu("Pause Menu");
        Font menuFont = new Font("Arial", Font.BOLD, 16);

        String[] menuItems = { "Continue", "Restart", "Menu" };
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
                        SwingUtilities.getWindowAncestor(Map.this).dispose();

                        GameMenu menu = new GameMenu();
                        menu.setVisible(true);
                    }
                }
            });

            pauseMenu.add(menuItem);
        }
    }

    Color brown = new Color(165, 42, 42);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(mapImage, 0, 0, this.getWidth(), this.getHeight(), this);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int alpha = 20;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                int x = j * boxWidth;
                int y = i * boxHeight;
                int width = (j == boxWidth - 1) ? screenSize.width - x : boxWidth;
                int height = (i == HEIGHT - 1) ? screenSize.height - y : boxHeight;

                Color fillColor = null;

                switch (map[i][j]) {
                    case 0:
                        fillColor = new Color(0, 128, 0, alpha);
                        break;
                    case 1:
                        fillColor = new Color(128, 128, 128, alpha);
                        break;
                    case 2:
                        fillColor = new Color(165, 42, 42, alpha);
                        break;
                    case 3:
                        fillColor = new Color(255, 0, 0, alpha);
                        break;
                    case 4:
                        fillColor = new Color(0, 0, 255, alpha);
                        break;
                    case 5:
                        fillColor = new Color(0, 255, 255, alpha);
                        break;
                }

                if (fillColor != null) {
                    g.setColor(fillColor);
                    g.fillRect(x, y, width, height);
                }

                g.setColor(Color.BLACK);
                g.drawRect(x, y, width, height);
            }
        }

        if (pauseButtonImage != null) {
            int x = getWidth() - pauseButtonImage.getWidth() - 25;
            int y = 14;
            g.drawImage(pauseButtonImage, x, y, this);
        }
    }

    private boolean isPauseButtonClicked(int x, int y) {
        int pauseButtonX = getWidth() - pauseButtonImage.getWidth() - 10;
        int pauseButtonY = 10;
        return x >= pauseButtonX && x <= pauseButtonX + pauseButtonImage.getWidth() && y >= pauseButtonY
                && y <= pauseButtonY + pauseButtonImage.getHeight();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game Map");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Map mapPanel = new Map();
            frame.add(mapPanel);
            frame.pack();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
            frame.setVisible(true);
        });
    }
}

class TransparentPanel extends JPanel {

    public TransparentPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}