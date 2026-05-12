/**
 * Liam vs Evil Liam 2: Ultimate edition is the pinnacle of gaming.
 * Fueled with the tears of many programmers, it delivers a unique experience catered towards casual players and extreme gamers alike.
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class main
{
    private static final int WINDOW_WIDTH = 750; // each tile is 50 pixels square, 750 leaves space for 13 tiles + edges
    private static final int WINDOW_HEIGHT = 750;

    private static final int WINDOW_BAR_HEIGHT = 33;

    private static final int TILE_SIZE = 50;

    float fps = 60;


    private int playerX = 0;
    private int playerY = 1;


    // level for testing. Will make level be inputted as string before playing later.
    int[][] wallsOne = {
        {1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1},
    };

    int[][] wallsTwo = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    public main()
    {
        // creates 'window'
        JFrame frame = new JFrame("Liam vs Evil Liam 2");

        // window parameters
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.setResizable(false);
        frame.setIgnoreRepaint(true);

        //window size
        frame.getContentPane().setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        frame.pack();

        frame.setVisible(true);

        // puts window in center of screen
        frame.setLocationRelativeTo(null); 
        frame.createBufferStrategy(2);
        frame.requestFocus();

        frame.addKeyListener(new TAdapter());

        update(frame);

        // timer which calls 'update' fps times per second
        Timer updateTimer = new Timer((int) (1000.0 / fps), e -> update(frame));

        updateTimer.start();
    }

    // runs every frame, the input 'frame' variable is the window
    private void update(JFrame frame) {

        BufferStrategy bufferStrategy = frame.getBufferStrategy();
        if (bufferStrategy == null) {
            frame.createBufferStrategy(2); // Double buffering
            bufferStrategy = frame.getBufferStrategy();
        }

        Graphics g = bufferStrategy.getDrawGraphics();

        g.translate(8, 0);

        drawWalls(g);
        drawPlayer(g);

        g.dispose();
        bufferStrategy.show();
    }

    private void drawWalls(Graphics g) {
        g.setColor(Color.BLUE); // wall color

        g.fillRect(0, WINDOW_BAR_HEIGHT, TILE_SIZE * 15, TILE_SIZE); // top
        g.fillRect(0, WINDOW_BAR_HEIGHT, TILE_SIZE, TILE_SIZE * 15); // left
        g.fillRect(0, WINDOW_BAR_HEIGHT + TILE_SIZE * 14, TILE_SIZE * 15, TILE_SIZE); // bottom
        g.fillRect(TILE_SIZE * 14, WINDOW_BAR_HEIGHT, TILE_SIZE, TILE_SIZE * 15); // right

        g.setColor(Color.MAGENTA); // wall color


        for (int y = 0; y < wallsOne.length; y++) {
            for (int x = 0; x < wallsOne[y].length; x++) {
                if (wallsOne[y][x] == 1) {
                    g.fillRect(TILE_SIZE + x * TILE_SIZE, TILE_SIZE + y * TILE_SIZE + WINDOW_BAR_HEIGHT, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }

    private void drawPlayer(Graphics g) {
        g.setColor(Color.RED); // player color

        g.fillOval(TILE_SIZE + playerX * TILE_SIZE, TILE_SIZE + playerY * TILE_SIZE + WINDOW_BAR_HEIGHT, TILE_SIZE, TILE_SIZE);
    }

    // class for detecting key presses
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)) {
                System.out.println("left");
            }

            if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)) {
                System.out.println("right");
            }

            if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W)) {
                System.out.println("up");
            }

            if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)) {
                System.out.println("down");
            }

            if ((key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER)) {
                System.out.println("space / enter");
            }
        }
    }
}