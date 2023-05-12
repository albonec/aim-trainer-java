import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Game extends JPanel {

    public static final int WIDTH = 800;   // Width of the game window
    public static final int HEIGHT = 600;  // Height of the game window

    private boolean isRunning;
    private Thread gameThread;

    private Crosshair crosshair;
    private List<Target> targets;

    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();

        // Initialize the crosshair
        crosshair = new Crosshair();

        // Initialize the targets list
        targets = new ArrayList<>();

        // Add mouse listener to track mouse clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                checkTargetInteractions(e.getX(), e.getY());
            }
        });

        // Add mouse motion listener to track mouse movement
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                crosshair.setTargetPosition(e.getX(), e.getY());
            }
        });
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
        updateTargets();

        // Spawn targets automatically
        spawnTarget();
    }

    private void updateTargets() {
        Iterator<Target> iterator = targets.iterator();
        while (iterator.hasNext()) {
            Target target = iterator.next();
            target.update();
            if (target.isOffscreen()) {
                iterator.remove();
            }
        }
    }

    private void render() {
        // Get the graphics context
        Graphics2D g = (Graphics2D) getGraphics();

        // Clear the screen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Render the crosshair
        crosshair.render(g);

        // Render the targets
        for (Target target : targets) {
            target.render(g);
        }

        // Dispose the graphics context
        g.dispose();

        // Synchronize the display on some systems
        Toolkit.getDefaultToolkit().sync();
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void spawnTarget() {
        Random random = new Random(1);
        if (random.nextDouble() < 0.01) {  // Adjust the probability as desired
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            double depth = random.nextDouble();

            Target target = new Target(x, y, depth);
            targets.add(target);
        }
    }

    private void checkTargetInteractions(int x, int y) {
        Iterator<Target> iterator = targets.iterator();
        while (iterator.hasNext()) {
            Target target = iterator.next();
            if (target.containsPoint(x, y)) {
                iterator.remove();
                // Perform any additional actions when a target is clicked
                // For example, increment a score counter or play a sound effect
            }
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