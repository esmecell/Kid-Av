import javax.swing.JFrame;

/**
 * Entry point for Backyard Adventure.
 * Launches the game window and starts the game loop.
 */
public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Backyard Adventure");
        GamePanel gamePanel = new GamePanel();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null); // center on screen
        window.setVisible(true);

        gamePanel.startGame();
    }
}
