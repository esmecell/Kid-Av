import javax.swing.JFrame;

/**
 Punto de entrada para el juego.
 * Abre la ventana del juego e inicia el bucle del juego.
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
