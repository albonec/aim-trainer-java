import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {
    private static JButton clearScoresButton;
    private static Component spacing = Box.createRigidArea(new Dimension(0, 50));

    public ButtonPanel() {
        clearScoresButton = new JButton("Clear Scores");
        add(spacing);
        add(clearScoresButton);

        clearScoresButton.addActionListener(e -> {
            Game.score = 0;
            Game.missedTargets = 0;
            Game.scoreWindow.setMissedTargets(0);
            Game.scoreWindow.setScore(0);
        });
    }
}
