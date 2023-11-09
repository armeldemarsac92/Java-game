package PixelMap;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Exceptions.*;
import GameLogic.*;

public class GamePanel extends JPanel {

    private static final int GRID_CELL_SIZE = 50; // Change this to your desired grid size
    private static final int MAP_WIDTH = 3840;
    private static final int MAP_HEIGHT = 2160;
    private Image backgroundImage;
    private List<Tower> towers = new ArrayList<Tower>();

    public GamePanel() {
        // Set the size of the panel to match the map
        setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("assets/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error here - perhaps set a default background color instead
        }
        this.towers.add(new Tower(1,1,1,1, new Coordinates(500, 400)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }

        // Draw the grid on top of the background image
        // g.setColor(Color.GRAY); // Set the grid color to gray, or any other color you prefer
        // for (int x = 0; x <= MAP_WIDTH; x += GRID_CELL_SIZE) {
        //     g.drawLine(x, 0, x, MAP_HEIGHT);
        // }
        // for (int y = 0; y <= MAP_HEIGHT; y += GRID_CELL_SIZE) {
        //     g.drawLine(0, y, MAP_WIDTH, y);
        // }

        this.towers.get(0).draw(g, GRID_CELL_SIZE);
    }

    protected void paintTower(Graphics g) {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tower Defense Game");
            GamePanel panel = new GamePanel();
            frame.add(panel);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        });
    }
}
