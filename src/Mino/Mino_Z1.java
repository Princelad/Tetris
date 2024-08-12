package Mino;

import java.awt.*;

public class Mino_Z1 extends Mino {

    public Mino_Z1() {
        create(Color.red);
    }

    public void setXY(int x, int y) {
        //     o
        //  o  o
        //  o
        block[0].x = x;
        block[0].y = y;
        block[1].x = block[0].x;
        block[1].y = block[0].y - Block.SIZE;
        block[2].x = block[0].x - Block.SIZE;
        block[2].y = block[0].y;
        block[3].x = block[0].x - Block.SIZE;
        block[3].y = block[0].y + Block.SIZE;
    }

    public void getDirection1() {
        //     o
        //  o  o
        //  o
        tempBlock[0].x = block[0].x;
        tempBlock[0].y = block[0].y;
        tempBlock[1].x = block[0].x;
        tempBlock[1].y = block[0].y - Block.SIZE;
        tempBlock[2].x = block[0].x - Block.SIZE;
        tempBlock[2].y = block[0].y;
        tempBlock[3].x = block[0].x - Block.SIZE;
        tempBlock[3].y = block[0].y + Block.SIZE;

        updateXY(1);
    }

    public void getDirection2() {
        //  o  o
        //     o  o
        tempBlock[0].x = block[0].x;
        tempBlock[0].y = block[0].y;
        tempBlock[1].x = block[0].x + Block.SIZE;
        tempBlock[1].y = block[0].y;
        tempBlock[2].x = block[0].x;
        tempBlock[2].y = block[0].y - Block.SIZE;
        tempBlock[3].x = block[0].x - Block.SIZE;
        tempBlock[3].y = block[0].y - Block.SIZE;

        updateXY(2);
    }

    public void getDirection3() {
        getDirection1();
    }

    public void getDirection4() {
        getDirection2();
    }
}
