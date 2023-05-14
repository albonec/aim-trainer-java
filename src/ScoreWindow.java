import javax.swing.*;
import java.awt.*;

public class ScoreWindow extends JPanel {
    private static JLabel scoreLabel;
    private static JLabel missedLabel;
    private static JLabel accuracyLabelTitle;
    private static JLabel accuracyLabelBody;

    public ScoreWindow() {
        scoreLabel = new JLabel("Score: 0");
        missedLabel = new JLabel("Missed: 0");
        accuracyLabelTitle = new JLabel("Accuracy:");
        accuracyLabelBody = new JLabel("100%");
        add(scoreLabel);
        add(missedLabel);
        add(accuracyLabelTitle);
        add(accuracyLabelBody);
    }

    public void setScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void setMissedTargets(int missedTargets) {
        missedLabel.setText("Missed: " + missedTargets);
    }

    public void setAccuracyLabelBody(float accuracy) {
        accuracyLabelBody.setText(accuracy + "%");
    }

    @Override
    public Dimension getPreferredSize() {
        int width = 100; // Specify the desired width
        int height = super.getPreferredSize().height;
        return new Dimension(width, height);
    }

}