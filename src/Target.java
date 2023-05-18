import java.awt.*;
import java.util.Random;

// Target class gives attributes rules and color to targets (circles on screen)
public class Target {

    private int x; //size
    private int y;
    private double depth; // Dilation to simulate depth variance
    private int lifespan;

    private Color color; // Color of the target

    public Target(int x, int y, double depth) {
        this.x = x;
        this.y = y;
        this.depth = depth;
        this.lifespan = generateLifespan();

        /*
        Randomly selects color between the colors of the rainbow.
        The pre-defined colors in the Color class were too dull for this application.
         */

        Random random = new Random();
        int colorChoice = random.nextInt(6);
        switch (colorChoice) {
            case 0 -> color = new Color(0xFF0000);
            case 1 -> color = new Color(0xFF8800);
            case 2 -> color = new Color(0xFFFF00);
            case 3 -> color = new Color(0x00FF00);
            case 4 -> color = new Color(0x0000FF);
            case 5 -> color = new Color(0x8800FF);
        }
    }

    public void update() {                         // Decrements the lifespan to make the targets disappear.
        switch(Game.sleepIntervalMillis) {
            case 67 -> lifespan -= 4;
            case 33 -> lifespan -= 2;
            case 22 -> lifespan -= 1.5;
            case 17 -> lifespan -= 1;    // The speed at which this decreases is not affected by framerate
        }
    }

    public boolean isOffscreen() {
        return lifespan <= 0;
    }

    public boolean containsPoint(int x, int y) { // Checks whether the point (x,y) is inside the target's area
        int radius = (int) (100 * depth);
        int centerX = this.x + radius;
        int centerY = this.y + radius;
        return Math.pow(centerX - x, 2) + Math.pow(centerY - y, 2) <= Math.pow(radius, 2);
    }

    public void render(Graphics2D g) { // Renders the target object within the given Graphics2D context.
        int radius = (int) (100 * depth);
        g.setColor(color);
        g.fillOval(x, y, radius * 2, radius * 2);
    }

    private int generateLifespan() { // Assigns a value for the lifespan of the Target object
        return (int) ((int) (depth * 100) - 0.5);
    }
}