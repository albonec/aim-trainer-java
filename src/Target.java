import java.awt.*;

public class Target {

    private int x;
    private int y;
    private double depth;
    private int lifespan;

    public Target(int x, int y, double depth) {
        this.x = x;
        this.y = y;
        this.depth = depth;
        this.lifespan = generateLifespan();
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
        g.setColor(Color.RED);
        g.fillOval(x, y, radius * 2, radius * 2);
    }

    private int generateLifespan() {
        return (int) (depth * 100 + 50);
    }
}