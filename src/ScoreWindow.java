import javax.swing.*;
import java.awt.*;

public class ScoreWindow extends JFrame {

    private JLabel scoreLabel;

    public ScoreWindow() {
        setTitle("Score");
        setSize(200, 100);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(scoreLabel);
    }

    public void setScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void close() {
        this.setVisible(false);
        this.dispose();
    }

}