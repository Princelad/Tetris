package Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

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
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

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

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;

        // Set the starting Mino
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

    }

    private Mino pickMino() {

        // Pick a random Mino
        Mino mino = null;
        int i = new Random().nextInt(7);

        switch (i) {
            case 0 -> mino = new Mino_L1();
            case 1 -> mino = new Mino_L2();
            case 2 -> mino = new Mino_Square();
            case 3 -> mino = new Mino_Bar();
            case 4 -> mino = new Mino_T();
            case 5 -> mino = new Mino_Z1();
            case 6 -> mino = new Mino_Z2();
        }

        return mino;
    }

    public void update() {

        // Checking if mino is active or not
        if (!currentMino.active) {
            //if the mino is not active putting it in the staticBlocks arraylist.
            staticBlocks.add(currentMino.block[0]);
            staticBlocks.add(currentMino.block[1]);
            staticBlocks.add(currentMino.block[2]);
            staticBlocks.add(currentMino.block[3]);

            currentMino.deactivating = false;

            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

            // When minos are inactive, check if line can be deleted.
            checkDelete();
        } else {
            currentMino.update();
        }
    }

    private void checkDelete() {

        int x = left_x;
        int y = top_y;
        int blockCount = 0;

        while (x < right_x && y < bottom_y) {

            for (Block block : staticBlocks) {
                if (block.x == x && block.y == y) {
                    // Increase the block count for a static block
                    blockCount++;
                }
            }

            x += Block.SIZE;

            if (x == right_x) {

                // Checking if the row is completely full or not
                if (blockCount == 12) {
                    for (int i = staticBlocks.size() - 1; i > -1; i--) {
                        // removing all the blocks on the current y level
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }

                    // Line has been removed now we need to slide all the static blocks down
                    for (Block block : staticBlocks) {
                        // If the block is above the y then slide it down
                        if (block.y < y) {
                            block.y += Block.SIZE;
                        }
                    }
                }

                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }
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
        if (currentMino != null) {
            currentMino.draw(g);
        }

        // Draw next Mino
        nextMino.draw(g);

        // draw static blocks
        for (Block block : staticBlocks) {
            block.draw(g);
        }

        // Draw Pause
        g.setColor(Color.yellow);
        g.setFont(g.getFont().deriveFont(50f));
        if (keyHandler.pausePressed) {
            x = left_x + 70;
            y = top_y + 320;
            g.drawString("PAUSED", x, y);
        }

    }

}
