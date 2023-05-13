import java.awt.*;

public class Crosshair {

    private int x;
    private int y;

    public void setTargetPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.drawLine(x - 10, y, x + 10, y);
        g.drawLine(x, y - 10, x, y + 10);
    }
}