package PixelMap;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import GameLogic.*;

public class Game implements Runnable {

    private JFrame frame;
    private GamePanel gamePanel;
    private boolean running = true;
    private final int FPS = 60;
    private final long targetTime = 1000 / FPS;

    public Game() {
        gamePanel = new GamePanel();
        frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        // No need to make frame visible here; it will be done in the EDT.
    }

    public void run() {
        final int TARGET_FPS = 60;
        final long TARGET_TIME = 1000 / TARGET_FPS; // Target time per frame in milliseconds
        new Barbarian(new Coordinates(10, 600));
    
        while (running) {
            long start = System.currentTimeMillis();
    
            updateGame(); // Update the game state
            gamePanel.repaint(); // Request a repaint
    
            long duration = System.currentTimeMillis() - start; // Time taken for updating and rendering
            long waitTime = TARGET_TIME - duration; // Time to wait
    
            if (waitTime > 0) {
                try {
                    Thread.sleep(waitTime); // Wait until the end of the target frame time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break; // Exit the game loop if the thread was interrupted
                }
            }
        }
    }
    

    // public void run() {
        
    //     long startTime, elapsed, wait;
    //     int frame = 0;

    //     new Barbarian(1, 2, 2, 2, 2, 1, new Coordinates(10, 600));

    //     while (running) {
    //         startTime = System.nanoTime();
    //         frame ++;
    //         System.out.println(frame);
    //         updateGame(); // Update the game state
    //         gamePanel.repaint(); // Render the game by repainting the panel

    //         elapsed = System.nanoTime() - startTime;
    //         wait = targetTime - elapsed / 1000000;
    //         if (wait <= 0) {
    //             wait = 5; // Just to yield the thread if we're running behind
    //         }

    //         try {
    //             Thread.sleep(wait);
    //         } catch (InterruptedException e) {
    //             e.printStackTrace();
    //         }

    //     }
    // }

    private void updateGame() {
        for(Unit unit : GlobalUnits.getGlobalUnits()){
            if(unit instanceof Mob){
                ((Mob)unit).move();
            }
        }
        // Update your game state here
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            game.frame.setVisible(true);
            new Thread(game).start(); // Start the game loop in a new thread.
            
        });
    }
}
