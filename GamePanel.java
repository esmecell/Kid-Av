import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Core game panel: owns the game loop (via Swing Timer), handles keyboard
 * input, updates game state, and renders everything each frame.
 */
public class GamePanel extends JPanel implements ActionListener {
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;
    private static final int FPS = 60;

    private Timer timer;
    private Player player;
    private List<Item> items;
    private List<Rectangle> obstacles;
    private Rectangle playArea;

    private boolean up, down, left, right;
    private int score = 0;
    private boolean gameWon = false;

    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(new Color(150, 200, 110)); // grassy backyard color
        setFocusable(true);

        playArea = new Rectangle(20, 20, PANEL_WIDTH - 40, PANEL_HEIGHT - 40);

        player = new Player(PANEL_WIDTH / 2 - Player.WIDTH / 2, PANEL_HEIGHT / 2 - Player.HEIGHT / 2);

        obstacles = new ArrayList<>();
        obstacles.add(new Rectangle(150, 150, 60, 60));
        obstacles.add(new Rectangle(550, 200, 60, 60));
        obstacles.add(new Rectangle(300, 400, 60, 60));

        items = new ArrayList<>();
        items.add(new Item(100, 100));
        items.add(new Item(700, 100));
        items.add(new Item(100, 480));
        items.add(new Item(700, 480));
        items.add(new Item(400, 250));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                setKeyState(e.getKeyCode(), true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                setKeyState(e.getKeyCode(), false);
            }
        });

        timer = new Timer(1000 / FPS, this);
    }

    private void setKeyState(int keyCode, boolean pressed) {
        switch (keyCode) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> up = pressed;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> down = pressed;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> left = pressed;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> right = pressed;
        }
    }

    public void startGame() {
        requestFocusInWindow();
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    private void update() {
        if (gameWon) return;

        player.move(up, down, left, right, playArea, obstacles);

        for (Item item : items) {
            if (!item.isCollected() && player.getBounds().intersects(item.getBounds())) {
                item.collect();
                score++;
            }
        }

        if (score == items.size()) {
            gameWon = true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawGround(g2);

        g2.setColor(new Color(90, 60, 20));
        for (Rectangle obstacle : obstacles) {
            g2.fillOval(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
            g2.setColor(new Color(40, 110, 40));
            g2.fillOval(obstacle.x - 10, obstacle.y - 20, obstacle.width + 20, obstacle.height);
            g2.setColor(new Color(90, 60, 20));
        }

        for (Item item : items) {
            item.draw(g2);
        }

        player.draw(g2);

        drawHud(g2);

        if (gameWon) {
            drawWinMessage(g2);
        }
    }

    private void drawGround(Graphics2D g2) {
        g2.setColor(new Color(140, 190, 100));
        for (int gx = 0; gx < PANEL_WIDTH; gx += 40) {
            for (int gy = 0; gy < PANEL_HEIGHT; gy += 40) {
                if ((gx / 40 + gy / 40) % 2 == 0) {
                    g2.fillRect(gx, gy, 40, 40);
                }
            }
        }
    }

    private void drawHud(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 140));
        g2.fillRoundRect(10, 10, 160, 34, 10, 10);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 18));
        g2.drawString("Stars: " + score + " / " + items.size(), 22, 34);
    }

    private void drawWinMessage(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 36));
        String msg = "You collected all the stars!";
        FontMetrics fm = g2.getFontMetrics();
        int msgX = (PANEL_WIDTH - fm.stringWidth(msg)) / 2;
        g2.drawString(msg, msgX, PANEL_HEIGHT / 2);
    }
}
