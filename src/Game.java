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

    public static int score = 0; // Number of targets clicked by player
    public static int missedTargets = 0; // Number of targets missed by player
    private boolean isRunning;

    public static int sleepIntervalMillis = 33; // Interval to govern the framerate

    public static ScorePanel scoreWindow = new ScorePanel(); // instance of ScoreWindow which is rendered.
    private Thread gameThread; // Thread that runs the current Game.

    private Crosshair crosshair; // Crosshair which is rendered.
    private List<Target> targets; // List to store all extant targets.
    private Random random; // Pseudo-Random Number Generator

    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();

        // Initialize the crosshair
        crosshair = new Crosshair();

        // Initialize the targets list
        targets = new ArrayList<>();

        scoreWindow.setVisible(true);

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
        // Initialize the pseudo-random number generator
        random = new Random();
    }

    public void start() { // Start a Game on gameThread
        isRunning = true;
        gameThread = new Thread(this::gameLoop);
        gameThread.start();
    }

    private void gameLoop() { // Core execution loop of the game
        while (isRunning) {
            update();
            render();
            sleep(sleepIntervalMillis);  // Sleep for an interval (this directly affects the framerate of the game).
            scoreWindow.setAccuracyLabelBody(score, missedTargets);
        }
    }

    private void update() { //update game objects
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
                missedTargets++;
                scoreWindow.setMissedTargets(missedTargets);
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

    private void sleep(long millis) { //Catcher/wrapper method for Thread.sleep, used as a substitute
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void spawnTarget() {
        if (random.nextDouble() < 0.01) {  // Adjust the probability as desired
            int targetSize = 100; // Adjust the size of the target

            int targetWidth = targetSize * 2; // Horizontal diameter of target (ellipse)
            int targetHeight = targetSize * 2; // Vertical diameter of target (ellipse)

            int x = random.nextInt(WIDTH - targetWidth);
            int y = random.nextInt(HEIGHT - targetHeight);

            double depth = random.nextDouble(0.25) + 0.25;

            Target target = new Target(x, y, depth);
            targets.add(target);
        }
    }

    private void checkTargetInteractions(int x, int y) {
        Iterator<Target> iterator = targets.iterator(); // Creates dummy variable to import the list of targets
        while (iterator.hasNext()) { // Iterates through each Target object in the Iterator.
            Target target = iterator.next();
            if (target.containsPoint(x, y)) {
                iterator.remove(); // Removes target if the passed parameter point (x,y) is inside the Target.
                score++; // Increments the score and updates the scoreLabel JLabel in the GUI.
                scoreWindow.setScore(score);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("albonec's Aim Trainer");

            // Create the game and score panel
            Game game = new Game();
            ScorePanel scoreWindow = new ScorePanel();
            ButtonPanel buttonPanel = new ButtonPanel();

            // Create a container panel to hold game and score panel
            JPanel containerPanel = new JPanel(new BorderLayout());
            containerPanel.add(game, BorderLayout.WEST);
            containerPanel.add(buttonPanel, BorderLayout.SOUTH);
            containerPanel.add(scoreWindow, BorderLayout.EAST);

            // Configure the GUI.
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