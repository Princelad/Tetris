package Mino;

import Main.gamePanel;
import Main.keyHandler;
import Main.playManager;

import java.awt.*;

public class Mino {

    public Block[] block = new Block[4];
    public Block[] tempBlock = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1; // Each mino has four direction (1/2/3/4)
    boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactivating;
    int deactivateCounter = 0;

    public void create(Color c) {
        block[0] = new Block(c);
        block[1] = new Block(c);
        block[2] = new Block(c);
        block[3] = new Block(c);
        tempBlock[0] = new Block(c);
        tempBlock[1] = new Block(c);
        tempBlock[2] = new Block(c);
        tempBlock[3] = new Block(c);
    }

    public void setXY(int x, int y) {
    }

    public void updateXY(int direction) {

        checkRotationCollision();

        if (!leftCollision && !rightCollision && !bottomCollision) {
            this.direction = direction;
            block[0].x = tempBlock[0].x;
            block[0].y = tempBlock[0].y;
            block[1].x = tempBlock[1].x;
            block[1].y = tempBlock[1].y;
            block[2].x = tempBlock[2].x;
            block[2].y = tempBlock[2].y;
            block[3].x = tempBlock[3].x;
            block[3].y = tempBlock[3].y;
        }
    }

    public void getDirection1() {
    }

    public void getDirection2() {
    }

    public void getDirection3() {
    }

    public void getDirection4() {
    }

    public void checkMovementCollision() {

        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        // checking for static block collision
        checkStaticBlockCollision();

        // Check for frame collision
        // left wall
        for (Block value : block) {
            if (value.x == playManager.left_x) {
                leftCollision = true;
            }
        }
        // right wall
        for (Block value : block) {
            if (value.x + Block.SIZE == playManager.right_x) {
                rightCollision = true;
            }
        }
        // bottom wall
        for (Block value : block) {
            if (value.y + Block.SIZE == playManager.bottom_y) {
                bottomCollision = true;
            }
        }
    }

    public void checkRotationCollision() {

        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        // checking for static block collision
        checkStaticBlockCollision();

        // Check for frame collision
        // left wall
        for (Block value : tempBlock) {
            if (value.x < playManager.left_x) {
                leftCollision = true;
            }
        }
        // right wall
        for (Block value : tempBlock) {
            if (value.x + Block.SIZE > playManager.right_x) {
                rightCollision = true;
            }
        }
        // bottom wall
        for (Block value : tempBlock) {
            if (value.y + Block.SIZE > playManager.bottom_y) {
                bottomCollision = true;
            }
        }
    }

    private void checkStaticBlockCollision () {
        for (int i = 0; i < playManager.staticBlocks.size(); i++) {

            int targetX = playManager.staticBlocks.get(i).x;
            int targetY = playManager.staticBlocks.get(i).y;

            // check for bottom
            for (Block value : block) {
                if (value.x== targetX && value.y + Block.SIZE == targetY) {
                    bottomCollision = true;
                }
            }

            // check for left
            for (Block value : block) {
                if (value.x - Block.SIZE == targetX && value.y == targetY) {
                    leftCollision = true;
                }
            }

            // check for right
            for (Block value : block) {
                if (value.x + Block.SIZE == targetX && value.y == targetY) {
                    rightCollision = true;
                }
            }
        }
    }

    public void update() {

        if(deactivating) {
            deactivating();
        }

        // Move the mino
        if (keyHandler.upPressed) {
            switch (direction) {
                case 1 -> getDirection2();
                case 2 -> getDirection3();
                case 3 -> getDirection4();
                case 4 -> getDirection1();
            }

            keyHandler.upPressed = false;

            gamePanel.se.play(3, false);
        }

        checkMovementCollision();

        if (keyHandler.downPressed && !bottomCollision) {

            // Mino goes down
            block[0].y += Block.SIZE;
            block[1].y += Block.SIZE;
            block[2].y += Block.SIZE;
            block[3].y += Block.SIZE;

            // When moved down reset the auto drop counter
            autoDropCounter = 0;

            keyHandler.downPressed = false;

        }
        if (keyHandler.leftPressed && !leftCollision) {

            // Block goes left
            block[0].x -= Block.SIZE;
            block[1].x -= Block.SIZE;
            block[2].x -= Block.SIZE;
            block[3].x -= Block.SIZE;

            keyHandler.leftPressed = false;

        }
        if (keyHandler.rightPressed && !rightCollision) {

            // Block goes to right
            block[0].x += Block.SIZE;
            block[1].x += Block.SIZE;
            block[2].x += Block.SIZE;
            block[3].x += Block.SIZE;

            keyHandler.rightPressed = false;
        }

        if(bottomCollision){
            if (!deactivating) {
                gamePanel.se.play(4, false);
            }
            deactivating = true;
        } else {
            autoDropCounter++; // The counter increases every 60 frames
            if (autoDropCounter == playManager.dropInterval) {
                // Mino goes down
                block[0].y += Block.SIZE;
                block[1].y += Block.SIZE;
                block[2].y += Block.SIZE;
                block[3].y += Block.SIZE;
                autoDropCounter = 0;
            }
        }
    }

    private void deactivating() {
        deactivateCounter++;

        // Waiting for 45 frames for deactivation
        if(deactivateCounter == 45) {
            deactivateCounter = 0;
            checkMovementCollision(); // then checking for collision

            // If still colliding then deactivate.
            if(bottomCollision){
                active = false;
            }
        }
    }

    public void draw(Graphics2D g) {

        int margin = 2;
        g.setColor(block[0].c);
        g.fillRect(block[0].x + margin, block[0].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g.fillRect(block[1].x + margin, block[1].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g.fillRect(block[2].x + margin, block[2].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g.fillRect(block[3].x + margin, block[3].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
    }
}
