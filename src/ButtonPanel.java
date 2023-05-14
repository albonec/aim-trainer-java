import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {
    private static JButton startStopButton;
    private static JButton clearScoresButton;
    private static Component spacing = Box.createRigidArea(new Dimension(0, 50));

    public ButtonPanel() {
        startStopButton = new JButton("Start");
        clearScoresButton = new JButton("Clear Scores");
        add(startStopButton);
        add(spacing);
        add(clearScoresButton);
    }
}
