import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {

    /*
    Defining all objects in ButtonPanel
    */
    private static JButton clearScoresButton;
    private static JButton frameRateButton;
    private static Component spacing = Box.createRigidArea(new Dimension(0, 50)); // Vertical spacing for margins

    public ButtonPanel() {
        /*
        Initializes and places all GUI elements in the ButtonPanel.
         */
        clearScoresButton = new JButton("Clear Scores");
        frameRateButton = new JButton("Framerate: 30 FPS");
        add(spacing);
        add(clearScoresButton);
        add(frameRateButton);

        clearScoresButton.addActionListener(e -> { // Instructions to execute when the clearScoresButton is pressed.
            Game.score = 0;
            Game.missedTargets = 0;
            Game.scoreWindow.setMissedTargets(0);
            Game.scoreWindow.setScore(0);
        });

        frameRateButton.addActionListener(e -> { // Instructions to execute when the frameRateButton is pressed.
            if(Game.sleepIntervalMillis == 33) {
                Game.sleepIntervalMillis = 22;
                frameRateButton.setText("Framerate: 45 FPS");
            } else if (Game.sleepIntervalMillis == 22) {
                Game.sleepIntervalMillis = 17;
                frameRateButton.setText("Framerate: 60 FPS");
            } else if (Game.sleepIntervalMillis == 17) {
                Game.sleepIntervalMillis = 67;
                frameRateButton.setText("Framerate: 15 FPS");
            } else if (Game.sleepIntervalMillis == 67) {
                Game.sleepIntervalMillis = 33;
                frameRateButton.setText("Framerate: 30 FPS");
            }
        }); // Pause intervals by framerate: 67ms for 15 FPS, 33ms for 30 FPS (default), 22ms for 45 FPS, 17ms for 60 FPS.
    }
}
