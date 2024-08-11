package Main;

import java.awt.*;
import Mino.*;

public class playManager {

    // Main play area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    // Mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;

    // Others
    public static int dropInterval = 60; // Thus mino drops in every 60 frames

    public playManager() {

        // Main play area frame
        left_x = (gamePanel.WIDTH / 2) - (WIDTH / 2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        // Set the starting Mino
        currentMino = new Mino_T();
        currentMino.setXY(MINO_START_X, MINO_START_Y);

    }

    public void update() {
        currentMino.update();
    }

    public void draw(Graphics2D g) {

        // Draw main play area
        g.setColor(Color.white);
        g.setStroke(new BasicStroke(4f));
        g.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

        // Draw next mino frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g.drawRect(x, y, 200, 200);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawString("NEXT", x + 60, y + 60);

        // Drawing current Mino
        if(currentMino != null) {
            currentMino.draw(g);
        }

    }

}
