import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Game extends JPanel {

    public static final int WIDTH = 800;   // Width of the game window
    public static final int HEIGHT = 600;  // Height of the game window

    private int GAMESCORE = 0;
    private boolean isRunning;

    private ScoreWindow scoreWindow = new ScoreWindow();
    private Thread gameThread;

    private Crosshair crosshair;
    private List<Target> targets;
    private Random random;

    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();

        // Initialize the crosshair
        crosshair = new Crosshair();

        // Initialize the targets list
        targets = new ArrayList<>();

        scoreWindow.setVisible(true);
        scoreWindow.setLocation(this.getX() + WIDTH + 10, this.getY() + (HEIGHT - scoreWindow.getHeight()) / 2);

        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                "InvisibleCursor"));

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

        // Initialize the game
        initialize();
    }

    private void initialize() {
        // Initialize the random number generator
        random = new Random();
    }

    public void start() {
        isRunning = true;
        gameThread = new Thread(this::gameLoop);
        gameThread.start();
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

        // Render the targets
        for (Target target : targets) {
            target.render(g);
        }

        crosshair.render(g);

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
        if (random.nextDouble() < 0.01) {  // Adjust the probability as desired
            int targetSize = 100; // Adjust the size of the target

            int targetWidth = targetSize * 2;
            int targetHeight = targetSize * 2;

            int x = random.nextInt(WIDTH - targetWidth);
            int y = random.nextInt(HEIGHT - targetHeight);

            double depth = random.nextDouble(0.25) + 0.25;

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
                GAMESCORE++;
                scoreWindow.setScore(GAMESCORE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("albonec's Aim Trainer");

            // Create the game and score panel
            Game game = new Game();
            ScoreWindow scoreWindow = new ScoreWindow();

            // Create a container panel to hold game and score panel
            JPanel containerPanel = new JPanel(new BorderLayout());
            containerPanel.add(game, BorderLayout.WEST);
            containerPanel.add(scoreWindow, BorderLayout.EAST);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.getContentPane().add(containerPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Start the game
            game.start();
        });
    }
}