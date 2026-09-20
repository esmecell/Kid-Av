import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/** Un objeto coleccionable con forma de estrella disperso por la zona de juego.*/
public class Item {
    public static final int SIZE = 20;

    private final int x, y;
    private boolean collected = false;

    public Item(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    public void draw(Graphics2D g) {
        if (collected) return;

        g.setColor(new Color(255, 215, 0));
        int[] xs = new int[10];
        int[] ys = new int[10];
        int cx = x + SIZE / 2, cy = y + SIZE / 2;
        double outerR = SIZE / 2.0, innerR = outerR / 2.3;

        for (int i = 0; i < 10; i++) {
            double angle = Math.PI / 2 + i * Math.PI / 5;
            double r = (i % 2 == 0) ? outerR : innerR;
            xs[i] = (int) (cx + r * Math.cos(angle));
            ys[i] = (int) (cy - r * Math.sin(angle));
        }
        g.fillPolygon(xs, ys, 10);
    }
}
