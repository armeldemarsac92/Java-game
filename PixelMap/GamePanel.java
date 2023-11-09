import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {

    private static final int GRID_CELL_SIZE = 64; // Change this to your desired grid size
    private static final int MAP_WIDTH = 3840;
    private static final int MAP_HEIGHT = 2160;
    private Image backgroundImage;

    public GamePanel() {
        // Set the size of the panel to match the map
        setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("path/to/your/background/image.png"));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error here - perhaps set a default background color instead
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this);
        }

        // Draw the grid on top of the background image
        g.setColor(Color.GRAY); // Set the grid color to gray, or any other color you prefer
        for (int x = 0; x <= MAP_WIDTH; x += GRID_CELL_SIZE) {
            g.drawLine(x, 0, x, MAP_HEIGHT);
        }
        for (int y = 0; y <= MAP_HEIGHT; y += GRID_CELL_SIZE) {
            g.drawLine(0, y, MAP_WIDTH, y);
        }
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
