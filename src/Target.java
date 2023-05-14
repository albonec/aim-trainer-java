import java.awt.*;
import java.util.Random;

public class Target {

    private int x;
    private int y;
    private double depth;
    private int lifespan;

    private Color color;

    public Target(int x, int y, double depth) {
        this.x = x;
        this.y = y;
        this.depth = depth;
        this.lifespan = generateLifespan();

        Random random = new Random();
        int colorChoice = random.nextInt(6);
        switch (colorChoice) {
            case 0:
                color = new Color(0xFF0000);
                break;
            case 1:
                color = new Color(0xFF8800);
                break;
            case 2:
                color = new Color(0xFFFF00);
                break;
            case 3:
                color = new Color(0x00FF00);
                break;
            case 4:
                color = new Color(0x0000FF);
                break;
            case 5:
                color = new Color(0x8800FF);
                break;
        }
    }

    public void update() {
        lifespan--;
    }

    public boolean isOffscreen() {
        return lifespan <= 0;
    }

    public boolean containsPoint(int x, int y) {
        int radius = (int) (100 * depth);
        int centerX = this.x + radius;
        int centerY = this.y + radius;
        return Math.pow(centerX - x, 2) + Math.pow(centerY - y, 2) <= Math.pow(radius, 2);
    }

    public void render(Graphics2D g) {
        int radius = (int) (100 * depth);
        g.setColor(color);
        g.fillOval(x, y, radius * 2, radius * 2);
    }

    private int generateLifespan() {
        return (int) (depth * 100 + 50);
    }
}