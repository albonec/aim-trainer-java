import java.awt.*;

public class Crosshair {

    private static final int BASE_SIZE = 20;  // Size of the crosshair when closest to the viewer

    private int x;      // X coordinate of the crosshair
    private int y;      // Y coordinate of the crosshair
    private double depth;  // Depth value

    public Crosshair() {
        x = Game.WIDTH / 2;   // Start the crosshair at the center of the screen
        y = Game.HEIGHT / 2;
        depth = 0;   // Initial depth value
    }

    public void setTargetPosition(int targetX, int targetY) {
        // Update the crosshair's position based on the target's position
        x = targetX;
        y = targetY;
        updateDepth();
    }

    private void updateDepth() {
        // Calculate the depth value based on the crosshair's position
        depth = Math.sqrt((x - Game.WIDTH / 2) * (x - Game.WIDTH / 2) + (y - Game.HEIGHT / 2) * (y - Game.HEIGHT / 2)) / (Math.sqrt(Game.WIDTH * Game.WIDTH + Game.HEIGHT * Game.HEIGHT) / 2);
    }

    public void render(Graphics2D g) {
        int size = calculateSize();

        // Draw the crosshair as a plus sign
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2));

        g.drawLine(x - size, y, x + size, y);  // Draw horizontal line
        g.drawLine(x, y - size, x, y + size);  // Draw vertical line
    }

    private int calculateSize() {
        // Calculate the size based on the depth value
        double scaleFactor = 0.5;  // How quickly the size decreases with depth

        int size = (int) (BASE_SIZE * (1 - depth * scaleFactor));
        return Math.max(size, 1);  // Ensure the size is at least 1
    }
}