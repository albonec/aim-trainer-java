import java.awt.*;
import java.util.Random;

public class Target {

    private static final int SIZE = 40;  // Size of the target

    private int x;      // X coordinate of the target
    private int y;      // Y coordinate of the target
    private double depth;  // Depth value
    private int lifespan;  // Remaining lifespan of the target
    private boolean isVisible;  // Flag indicating if the target is visible

    private Random random;

    public Target(int x, int y, double depth) {
        this.x = x;
        this.y = y;
        this.depth = depth;

        // Initialize the lifespan of the target
        this.lifespan = generateLifespan();

        // Initialize the visibility of the target
        this.isVisible = true;

        // Initialize the random number generator
        random = new Random();
    }

    public void update() {
        if (isVisible) {
            lifespan--;

            // Check if the target should disappear
            if (lifespan <= 0) {
                isVisible = false;
            }
        }
    }

    public void render(Graphics2D g) {
        if (isVisible) {
            int size = calculateSize();

            // Draw the target as a filled circle
            g.setColor(Color.BLUE);
            g.fillOval(x - size / 2, y - size / 2, size, size);
        }
    }

    public boolean isOffscreen() {
        return !isVisible;
    }

    public boolean containsPoint(int pointX, int pointY) {
        if (isVisible) {
            int size = calculateSize();
            int halfSize = size / 2;
            int centerX = x;
            int centerY = y;

            // Check if the given point is within the bounds of the target
            if (pointX >= centerX - halfSize && pointX <= centerX + halfSize &&
                    pointY >= centerY - halfSize && pointY <= centerY + halfSize) {
                return true;
            }
        }
        return false;
    }

    private int calculateSize() {
        // Calculate the size based on the depth value
        int baseSize = SIZE;
        double scaleFactor = 0.5;  // How quickly the size decreases with depth

        int size = (int) (baseSize * (1 - depth * scaleFactor));
        return Math.max(size, 1);  // Ensure the size is at least 1
    }

    private int generateLifespan() {
        // Generate a random lifespan for the target within a specified range
        int minLifespan = 100;
        int maxLifespan = 500;
        return random.nextInt(maxLifespan - minLifespan + 1) + minLifespan;
    }
}