package GraphicInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.File;

public class Map extends JPanel {

    private static final int WIDTH = 33;
    private static final int HEIGHT = 19;
    private int boxWidth;
    private int boxHeight;

    private int[][] map = new int[HEIGHT][WIDTH];

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

        JButton pauseButton = new JButton();
        pauseButton.setBounds(screenSize.width - 50, 10, 40, 40);

        try {
            Image img = ImageIO.read(new File("assets/bouton-pause.png"));
            Image resizedImg = img.getScaledInstance(pauseButton.getWidth(), pauseButton.getHeight(), Image.SCALE_SMOOTH);
            pauseButton.setIcon(new ImageIcon(resizedImg));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPopUp();
            }
        });

        this.setLayout(null);
        this.add(pauseButton);
    }

    private void showPopUp() {

        String[] options = {"Continue", "Restart", "Menu"};

        int choice = JOptionPane.showOptionDialog(this, 
        "What are u doing ?",
        "Pause",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.PLAIN_MESSAGE,
        null,
        options,
        options[0]);

        switch (choice) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }
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
                { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
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

    Color brown = new Color(165, 42, 42);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                int x = j * boxWidth;
                int y = i * boxHeight;
                int width = boxWidth;
                int height = boxHeight;

                if (j == WIDTH - 1) {
                    width = screenSize.width - x;
                }

                if (i == HEIGHT - 1) {
                    height = screenSize.height - y;
                }

                if (map[i][j] == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x, y, width, height);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, width, height);
                    continue;
                } else if (map[i][j] == 1) {
                    g.setColor(Color.GRAY);
                    g.fillRect(x, y, width, height);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, width, height);
                    continue;
                } else if (map[i][j] == 2) {
                    g.setColor(brown);
                    g.fillRect(x, y, width, height);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, width, height);
                    continue;
                } else if (map[i][j] == 3) {
                    g.setColor(Color.RED);
                    g.fillRect(x, y, width, height);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, width, height);
                    continue;
                } else if (map[i][j] == 4) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x, y, width, height);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, width, height);
                    continue;
                } else if (map[i][j] == 5) {
                    g.setColor(Color.CYAN);
                    g.fillRect(x, y, width, height);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, width, height);
                    continue;
                }

                g.fillRect(x, y, width, height);
            }
        }
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