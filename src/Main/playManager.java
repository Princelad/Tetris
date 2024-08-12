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
    boolean gameOver;

    // Effect
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    // Score
    int level = 1;
    int lines;
    int score;

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

            // check if game is over
            if (currentMino.block[0].x == MINO_START_X && currentMino.block[0].y == MINO_START_Y) {
                // this means that the block is collided and can't move
                // so it's x and y are same as next mino's
                gameOver = true;
                gamePanel.music.stop();
                gamePanel.se.play(2, false);
            }

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
        int lineCount = 0;

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

                    effectCounterOn = true;
                    effectY.add(y);

                    for (int i = staticBlocks.size() - 1; i > -1; i--) {
                        // removing all the blocks on the current y level
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }

                    lineCount++;
                    lines++;

                    // Drop Speed
                    // If line hits certain number, increase drop speed
                    // 1 is fastest
                    if (lines % 10 == 0 && dropInterval > 1) {

                        level++;
                        if (dropInterval > 10) {
                            dropInterval -= 10;
                        } else {
                            dropInterval += 10;
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

        // Add score
        if (lineCount > 0) {
            gamePanel.se.play(1, false);
            int singleLineScore = 10 * level;
            score += singleLineScore * lineCount;
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

        // Draw Score frame
        g.drawRect(x, top_y, 250, 300);
        x += 40;
        y = top_y + 90;
        g.drawString("Level: " + level, x, y);
        y += 70;
        g.drawString("Lines: " + lines, x, y);
        y += 70;
        g.drawString("Score: " + score, x, y);

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

        // Draw effect
        if (effectCounterOn) {
            effectCounter++;

            g.setColor(Color.red);
            for (Integer i : effectY) {
                g.fillRect(left_x, i, WIDTH, Block.SIZE);
            }

            if (effectCounter == 10) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

        // Draw Pause
        g.setColor(Color.yellow);
        g.setFont(g.getFont().deriveFont(50f));
        if (gameOver) {
            x = left_x + 25;
            y = top_y + 320;
            g.drawString("GAME OVER", x, y);
        } else if (keyHandler.pausePressed) {
            x = left_x + 70;
            y = top_y + 320;
            g.drawString("PAUSED", x, y);
        }

        // Draw Game title
        x = 35;
        y = top_y + 320;
        g.setColor(Color.white);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 60));
        g.drawString("Tetris", x + 200, y);

    }

}
