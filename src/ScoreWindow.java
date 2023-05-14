import javax.swing.*;
import java.awt.*;

public class ScoreWindow extends JPanel {
    private static JLabel scoreLabel;
    private static JLabel missedLabel;
    private static JLabel accuracyLabelTitle;
    private static JLabel accuracyLabelBody;
    private static Component spacing = Box.createRigidArea(new Dimension(0, 75));

    public ScoreWindow() {
        scoreLabel = new JLabel("Clicked: 0");
        missedLabel = new JLabel("Missed: 0");
        accuracyLabelTitle = new JLabel("Accuracy:");
        accuracyLabelBody = new JLabel("100%");
        add(scoreLabel);
        add(missedLabel);
        add(spacing);
        add(accuracyLabelTitle);
        add(accuracyLabelBody);
    }

    public void setScore(int score) {
        scoreLabel.setText("Clicked: " + score);
    }

    public void setMissedTargets(int missedTargets) {
        missedLabel.setText("Missed: " + missedTargets);
    }

    public void setAccuracyLabelBody(int score, int missedTargets) {
        accuracyLabelBody.setText(((float) score / (score + missedTargets) * 100) + "%");
    }

    @Override
    public Dimension getPreferredSize() {
        int width = 100; // Specify the desired width
        int height = super.getPreferredSize().height;
        return new Dimension(width, height);
    }

}