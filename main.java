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

    private static final int TILE_SIZE = 50;

    float fps = 600;


    private int playerStartX = 0;
    private int playerStartY = 1;

    private int playerX = 0;
    private int playerY = 1;

    private float playerXSmooth = 0;
    private float playerYSmooth = 1;

    private boolean canMove = true;

    // level for testing. Will make level be inputted as string before playing later.
    int[][] wallsOne = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
        {0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    int[][] wallsTwo = {
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
        {0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
    };

    // win arrays have one less column + row, because a win tile size is 2x2
    int[][] winArrayOne = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    int[][] winArrayTwo = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    // current walls are visible, collidable ones
    // alt and current flip when space key pressed
    private int[][] currentWalls = wallsOne;
    private int[][] altWalls = wallsTwo;

    private int[][] currentWins = winArrayOne;
    private int[][] altWins = winArrayTwo;


    private ImageIcon playerImage;
    private ImageIcon wallActiveImage;
    private ImageIcon wallUnactiveImage;
    private ImageIcon backgroundImage;
    private ImageIcon winImage;

    public main() {
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

        loadImages();

        resetPlayerPosition();

        update(frame);

        // timer which calls 'update' fps times per second
        Timer updateTimer = new Timer((int) (1000.0 / fps), e -> update(frame));

        updateTimer.start();
    }

    private String images_folder = "images";

    private void loadImages() {
        playerImage = new ImageIcon(images_folder + "/player.png");
        winImage = new ImageIcon(images_folder + "/win.png");
    }

    private void resetPlayerPosition() {
        playerX = playerStartX;
        playerY = playerStartY;
        playerXSmooth = playerStartX;
        playerYSmooth = playerStartY;
    }

    // runs every frame, the input 'frame' variable is the window
    private void update(JFrame frame) {

        BufferStrategy bufferStrategy = frame.getBufferStrategy();
        if (bufferStrategy == null) {
            frame.createBufferStrategy(2); // Double buffering
            bufferStrategy = frame.getBufferStrategy();
        }

        Graphics g = bufferStrategy.getDrawGraphics();

        g.translate(8, 32);

        clearGraphics(g);

        drawElements(g); // draws walls and win Liams

        canMove = false;

        if (playerXSmooth != playerX) {
            playerXSmooth += Math.signum(playerX - playerXSmooth) * 0.5f;
            playerXSmooth = (float) (Math.round(playerXSmooth * 10.0) / 10.0);
        } else if (playerYSmooth != playerY) {
            playerYSmooth += Math.signum(playerY - playerYSmooth) * 0.5f;
            playerYSmooth = (float) (Math.round(playerYSmooth * 10.0) / 10.0);
        } else {
            canMove = true;
        }

        drawPlayer(g);

        g.dispose();
        bufferStrategy.show();
    }

    private void clearGraphics(Graphics g) {
        g.setColor(Color.WHITE);

        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private void drawElements(Graphics g) {
        // border
        g.setColor(Color.decode("#70b0db")); // wall color

        g.fillRect(0, 0, TILE_SIZE * 15, TILE_SIZE); // top
        g.fillRect(0, 0, TILE_SIZE, TILE_SIZE * 15); // left
        g.fillRect(0, 0 + TILE_SIZE * 14, TILE_SIZE * 15, TILE_SIZE); // bottom
        g.fillRect(TILE_SIZE * 14, 0, TILE_SIZE, TILE_SIZE * 15); // right

        
        drawInactiveElements(g);
        drawActiveElements(g);
    }

    private void drawInactiveElements(Graphics g) {
        // Inactive walls
        g.setColor(Color.decode("#ebdcbe")); // wall color

        for (int y = 0; y < altWalls.length; y++) {
            for (int x = 0; x < altWalls[y].length; x++) {
                if (altWalls[y][x] == 1) {
                    g.fillRect(TILE_SIZE + x * TILE_SIZE, TILE_SIZE + y * TILE_SIZE + 0, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        // Inactive win places
        for (int y = 0; y < altWins.length; y++) {
            for (int x = 0; x < altWins[y].length; x++) {
                if (altWins[y][x] == 1) {
                    g.drawImage(winImage.getImage(), TILE_SIZE + (int)(x * TILE_SIZE), TILE_SIZE + (int)(y * TILE_SIZE), TILE_SIZE * 2, TILE_SIZE * 2, null);
                }
            }
        }
    }
    private void drawActiveElements(Graphics g) {
        // Active walls
        g.setColor(Color.decode("#70b0db")); // wall color

        for (int y = 0; y < currentWalls.length; y++) {
            for (int x = 0; x < currentWalls[y].length; x++) {
                if (currentWalls[y][x] == 1) {
                    g.fillRect(TILE_SIZE + x * TILE_SIZE, TILE_SIZE + y * TILE_SIZE + 0, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }

    // swaps between wall set one and two
    private void swapWalls() {
        if (currentWalls == wallsOne) {
            currentWalls = wallsTwo;
            altWalls = wallsOne;
        } else {
            currentWalls = wallsOne;
            altWalls = wallsTwo;
        }

        // if player inside wall, reset player position
        if (currentWalls[playerY][playerX] == 1) {
            resetPlayerPosition();
        }
    }

    private void drawPlayer(Graphics g) {
        g.drawImage(playerImage.getImage(), TILE_SIZE + (int)(playerXSmooth * TILE_SIZE), TILE_SIZE + (int)(playerYSmooth * TILE_SIZE), TILE_SIZE, TILE_SIZE, null);
    }

    // change variables are for which direction player has pressed eg. if xChange is -1 then left pressed
    private void calculateNextPlayerPosition(int xChange, int yChange) {

        while (playerY >= 0 && playerY < 13 && playerX >= 0 && playerX < 13 && currentWalls[playerY][playerX] != 1) {
            playerX += xChange;
            playerY += yChange;
        }
        playerX -= xChange;
        playerY -= yChange;
        System.out.println(playerX + "  " + playerY);
    }

    // class for detecting key presses
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (canMove) {
                if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)) {
                    System.out.println("left");
                    calculateNextPlayerPosition(-1, 0);
                }

                if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)) {
                    System.out.println("right");
                    calculateNextPlayerPosition(1, 0);
                }

                if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W)) {
                    System.out.println("up");
                    calculateNextPlayerPosition(0, -1);
                }

                if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)) {
                    System.out.println("down");
                    calculateNextPlayerPosition(0, 1);
                }

                if ((key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER)) {
                    System.out.println("space / enter");
                    swapWalls();
                }
            }
        }
    }
}