import java.awt.*;

public class Crosshair {

    /*
    Initializes position variables
    */
    private int x;
    private int y;

    public void setTargetPosition(int x, int y) { // Moves crosshair to position (x,y)
        this.x = x;
        this.y = y;
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(0xFFFFFF)); // Sets crosshair color to white over black background
        g.drawLine(x - 10, y, x + 10, y); // Draws cross pattern
        g.drawLine(x, y - 10, x, y + 10); // Draws cross pattern
    }
}