package game;

import javax.swing.*;
import java.awt.*;

public class gameFrame {

    public gameFrame() {
        JPanel sidePanel = new JPanel();
        JPanel gamePanel = new JPanel();
        JFrame frame = new JFrame();

        sidePanel.setBounds(400,0,100,700);
        sidePanel.setBackground(new Color(0x89b4fa));

        gamePanel.setBounds(100,0,300,700);
        gamePanel.setBackground(new Color(0xf38ba8));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("Tetris");
        frame.setSize(600, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.getContentPane().setBackground(new Color(0x1e1e2e));
        frame.add(gamePanel);
        frame.add(sidePanel);

    }
}
