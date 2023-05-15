import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    /*
    Defining all objects in ScorePanel
    */
    private static JLabel scoreLabel;
    private static JLabel missedLabel;
    private static JLabel accuracyLabelTitle; // The accuracyLabel is separated so that the
                                              // percentage appears below it when it becomes too long
    private static JLabel accuracyLabelBody;
    private static Component spacing = Box.createRigidArea(new Dimension(0, 75)); // Vertical spacing between JLabels by 75px

    public ScorePanel() {
        /*
        Initializes and places all GUI elements in the ScorePanel.
         */
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

    /*
        Setter methos/JLabel updaters
     */

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
    public Dimension getPreferredSize() { // This method sets a fixed width since default has no margins.
        int width = 110; // Specify the desired width
        int height = super.getPreferredSize().height; // Gets the default (full) height
        return new Dimension(width, height);
    }

}