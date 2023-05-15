import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {
    private static JButton clearScoresButton;
    private static JButton frameRateButton;
    private static Component spacing = Box.createRigidArea(new Dimension(0, 50));

    public ButtonPanel() {
        clearScoresButton = new JButton("Clear Scores");
        frameRateButton = new JButton("Framerate: 60 FPS");
        add(spacing);
        add(clearScoresButton);
        add(frameRateButton);

        clearScoresButton.addActionListener(e -> {
            Game.score = 0;
            Game.missedTargets = 0;
            Game.scoreWindow.setMissedTargets(0);
            Game.scoreWindow.setScore(0);
        });

        frameRateButton.addActionListener(e -> {
            if(Game.sleepIntervalMillis == 17) {
                Game.sleepIntervalMillis = 67;
                frameRateButton.setText("Framerate: 15 FPS");
            } else if (Game.sleepIntervalMillis == 67) {
                Game.sleepIntervalMillis = 33;
                frameRateButton.setText("Framerate: 30 FPS");
            } else if (Game.sleepIntervalMillis == 33) {
                Game.sleepIntervalMillis = 22;
                frameRateButton.setText("Framerate: 45 FPS");
            } else if (Game.sleepIntervalMillis == 22) {
                Game.sleepIntervalMillis = 17;
                frameRateButton.setText("Framerate: 60 FPS");
            }
        });
    }
}
