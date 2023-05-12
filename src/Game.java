import javax.swing.*;
import java.awt.*;

public class Game extends JPanel {

    private static final int WIDTH = 800;   // Width of the game window
    private static final int HEIGHT = 600;  // Height of the game window

    private boolean isRunning;
    private Thread gameThread;

    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    public void start() {
        isRunning = true;
        gameThread = new Thread(this::gameLoop);
        gameThread.start();
    }

    public void stop() {
        isRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void gameLoop() {
        while (isRunning) {
            update();
            render();
            sleep(16);  // Sleep for a short time (about 60 FPS)
        }
    }

    private void update() {
        // Update game logic here
    }

    private void render() {
        // Perform rendering here
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("FPS Aim Trainer");
            Game game = new Game();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(game);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            game.start();
        });
    }
}