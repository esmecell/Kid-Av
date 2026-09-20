import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
swap the
 * drawPlaceholder(...) call in draw() for drawing the BufferedImage instead cuando tengamo los archivos weon
 */
public class Player {
    public static final int WIDTH = 32;
    public static final int HEIGHT = 40;
    private static final int SPEED = 4;

    private int x, y;
    private Direction facing = Direction.DOWN;
    private boolean moving = false;

    // Temporizador de animación de ciclo de caminata simple,funciona bien con formas de marcador de posicion
    private int animTick = 0;
    private int animFrame = 0;

    // --- Sprite hook (ADAPTAR CUANDO TENGAMOS SPRITES) ---
    // private BufferedImage spriteSheet;
    // public void loadSprite(String path) throws java.io.IOException {
    //     spriteSheet = javax.imageio.ImageIO.read(new java.io.File(path));
    // }

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public void move(boolean up, boolean down, boolean left, boolean right, Rectangle bounds, java.util.List<Rectangle> obstacles) {
        int dx = 0, dy = 0;

        if (up) { dy -= SPEED; facing = Direction.UP; }
        if (down) { dy += SPEED; facing = Direction.DOWN; }
        if (left) { dx -= SPEED; facing = Direction.LEFT; }
        if (right) { dx += SPEED; facing = Direction.RIGHT; }

        moving = (dx != 0 || dy != 0);

        int newX = x + dx;
        int newY = y + dy;

        // Keep inside play area
        newX = Math.max(bounds.x, Math.min(newX, bounds.x + bounds.width - WIDTH));
        newY = Math.max(bounds.y, Math.min(newY, bounds.y + bounds.height - HEIGHT));

        Rectangle nextBoundsX = new Rectangle(newX, y, WIDTH, HEIGHT);
        Rectangle nextBoundsY = new Rectangle(x, newY, WIDTH, HEIGHT);

        boolean blockedX = false, blockedY = false;
        for (Rectangle obstacle : obstacles) {
            if (obstacle.intersects(nextBoundsX)) blockedX = true;
            if (obstacle.intersects(nextBoundsY)) blockedY = true;
        }

        if (!blockedX) x = newX;
        if (!blockedY) y = newY;

        if (moving) {
            animTick++;
            if (animTick > 8) {
                animTick = 0;
                animFrame = (animFrame + 1) % 2;
            }
        } else {
            animFrame = 0;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void draw(Graphics2D g) {
        // If a real sprite is loaded, draw it instead:
        // if (spriteSheet != null) {
        //     g.drawImage(spriteSheet, x, y, WIDTH, HEIGHT, null);
        //     return;
        // }
        drawPlaceholder(g);
    }

    /** Simple placeholder "kid" drawing: circle head + rectangle body, with a
     *  small bob animation and a direction-indicating triangle. */
    private void drawPlaceholder(Graphics2D g) {
        int bob = moving && animFrame == 1 ? -2 : 0;

        // body
        g.setColor(new Color(70, 130, 200));
        g.fillRoundRect(x, y + 14 + bob, WIDTH, HEIGHT - 14, 8, 8);

        // head
        g.setColor(new Color(255, 219, 172));
        g.fillOval(x + 4, y + bob, WIDTH - 8, 20);

        // hair
        g.setColor(new Color(90, 60, 30));
        g.fillArc(x + 4, y + bob - 2, WIDTH - 8, 16, 0, 180);

        // / indicador de orientacion 
        g.setColor(Color.WHITE);
        int cx = x + WIDTH / 2, cy = y + 10 + bob;
        switch (facing) {
            case UP -> g.fillOval(cx - 2, cy - 8, 4, 4);
            case DOWN -> g.fillOval(cx - 2, cy + 2, 4, 4);
            case LEFT -> g.fillOval(cx - 8, cy - 2, 4, 4);
            case RIGHT -> g.fillOval(cx + 6, cy - 2, 4, 4);
        }
    }
}
