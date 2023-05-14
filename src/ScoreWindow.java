import javax.swing.*;
import java.awt.*;

public class ScoreWindow extends JPanel {
    private static JLabel scoreLabel;

    public ScoreWindow() {
        scoreLabel = new JLabel("Score: 0");
        add(scoreLabel);
    }

    public void setScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    @Override
    public Dimension getPreferredSize() {
        int width = 100; // Specify the desired width
        int height = super.getPreferredSize().height;
        return new Dimension(width, height);
    }

}