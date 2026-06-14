/**
 * Liam vs Evil Liam 2: Ultimate edition isn't just a simple game; It is a great advancement into the artistic medium of gaming.
 * Fueled with the blood, sweat and tears of many programmers, it delivers a unique experience catered towards casual players and extreme gamers alike.
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
import java.util.Arrays;

public class main
{
    private static final int WINDOW_WIDTH = 750; // each tile is 50 pixels square, 750 leaves space for 13 tiles + edges
    private static final int WINDOW_HEIGHT = 750;

    private static final int TILE_SIZE = 50;

    float fps = 600;


    private int playerStartX = 0;
    private int playerStartY = 0;

    private int playerX = 0;
    private int playerY = 1;

    private float playerXSmooth = 0;
    private float playerYSmooth = 1;

    private boolean canMove = true;

    ArrayList<int[][]> levelData = new ArrayList<int[][]>(); // Big array containing all the wall placement information
    // Stored arrays are in the order data for wallsOne, data for wallsTwo, data for wallsOne etc
    // Also contains two integers at the start for the player position

    ArrayList<int[]> playerData = new ArrayList<int[]>(); // Similar to above array, but stores player start position

    // level for testing. Will make level be inputted as string before playing later.
    int[][] wallsOne = {
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

    // current walls are visible, collidable ones
    // alt and current flip when space key pressed
    private int[][] currentWalls = wallsOne;
    private int[][] altWalls = wallsTwo;

    private int currentLevel = 0;

    private boolean altImages = false;


    private ImageIcon playerImage;
    private ImageIcon wallActiveImageOne;
    private ImageIcon wallInactiveImageOne;
    private ImageIcon wallActiveImageTwo;
    private ImageIcon wallInactiveImageTwo;
    private ImageIcon backgroundImageOne;
    private ImageIcon backgroundImageTwo;
    private ImageIcon winActiveImage;
    private ImageIcon winInactiveImage;

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

        LoadImages();

        ResetPlayerPosition();

        PrepareLevel("1,3,00000000000000000000000000000000000000000000000000000000000000000000000010000000000001000000000000000000000000001110000111100100000000000010200020000000000000010000000000000000010000020000000000000000000000000000000000000000000000000000000000000000000000000000000010000000000000000111110011010011111000000001111100000000111110000000011111-0,3,11111111110000000000000000000000000000000000000000000000000000000000000010000000000001000000000000000000000000001110000111100100000000000010200020000000000000010000000000000000010000020000000000000000000000000000000000000000000000000000000000000000000000000000000010000000000000000111110011010011111000000001111100000000111110000000011111");

        LoadLevel(0);

        Update(frame);

        // timer which calls 'Update' fps times per second
        Timer updateTimer = new Timer((int) (1000.0 / fps), e -> Update(frame));

        updateTimer.start();
    }

    private String images_folder = "images";

    private void LoadImages() {
        playerImage = new ImageIcon(images_folder + "/player.png");
        wallActiveImageOne = new ImageIcon(images_folder + "/wall_active_1.png");
        wallInactiveImageOne = new ImageIcon(images_folder + "/wall_inactive_1.png");
        wallActiveImageTwo = new ImageIcon(images_folder + "/wall_active_2.png");
        wallInactiveImageTwo = new ImageIcon(images_folder + "/wall_inactive_2.png");
        backgroundImageOne = new ImageIcon(images_folder + "/background_1.png");
        backgroundImageTwo = new ImageIcon(images_folder + "/background_2.png");
        winActiveImage = new ImageIcon(images_folder + "/win_active_1.png");
        winInactiveImage = new ImageIcon(images_folder + "/win_inactive_1.png");
    }

    private void ResetPlayerPosition() {
        playerX = playerStartX;
        playerY = playerStartY;
        playerXSmooth = playerStartX;
        playerYSmooth = playerStartY;
    }

    // runs every frame, the input 'frame' variable is the window
    private void Update(JFrame frame) {

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

        // move the player image smoothly to final position
        if (playerXSmooth != playerX) {
            playerXSmooth += Math.signum(playerX - playerXSmooth) * 0.5f;
            playerXSmooth = (float) (Math.round(playerXSmooth * 10.0) / 10.0);
        } else if (playerYSmooth != playerY) {
            playerYSmooth += Math.signum(playerY - playerYSmooth) * 0.5f;
            playerYSmooth = (float) (Math.round(playerYSmooth * 10.0) / 10.0);
        } else {
            canMove = true;
        }

        // check if the player has won
        for (int tileY = 0; tileY < currentWalls.length; tileY++) {
            for (int tileX = 0; tileX < currentWalls.length; tileX++) {
                if (currentWalls[tileY][tileX] == 2 && Math.abs(playerXSmooth - tileX) < 0.5 && Math.abs(playerYSmooth - tileY) < 0.5) {
                    System.out.println("RAAAHHHH WIN WIN WIN");
                    LoadNextLevel();
                } else if (playerXSmooth >= 1 && playerYSmooth >= 1 && tileX > 0 && tileY > 0 ) {
                    if (currentWalls[tileY - 1][tileX - 1] == 2 && Math.abs(playerXSmooth - tileX) < 0.5 && Math.abs(playerYSmooth - tileY) < 0.5) {
                        System.out.println("RAAAHHHH WIN WIN WIN");
                    }
                } 
            }
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
        drawInactiveElements(g);
        drawActiveElements(g);
    }

    private void drawInactiveElements(Graphics g) {
        // Inactive
        ImageIcon wallImg = wallInactiveImageTwo;
        if (altImages) {
            wallImg = wallInactiveImageOne;
        }

        for (int y = 0; y < altWalls.length; y++) {
            for (int x = 0; x < altWalls[y].length; x++) {
                // walls
                if (altWalls[y][x] == 1) {
                    g.drawImage(wallImg.getImage(), TILE_SIZE + x * TILE_SIZE, TILE_SIZE + y * TILE_SIZE + 0, TILE_SIZE, TILE_SIZE, null);
                }
                // win positions
                else if (altWalls[y][x] == 2) {
                    g.drawImage(winInactiveImage.getImage(), TILE_SIZE + (int)(x * TILE_SIZE), TILE_SIZE + (int)(y * TILE_SIZE), TILE_SIZE * 2, TILE_SIZE * 2, null);
                }
            }
        }
    }
    private void drawActiveElements(Graphics g) { 
        // Active
        ImageIcon wallImg = wallActiveImageOne;
        if (altImages) {
            wallImg = wallActiveImageTwo;
        }

        for (int y = 0; y < currentWalls.length; y++) {
            for (int x = 0; x < currentWalls[y].length; x++) {
                // walls
                if (currentWalls[y][x] == 1) {
                    g.drawImage(wallImg.getImage(), TILE_SIZE + x * TILE_SIZE, TILE_SIZE + y * TILE_SIZE + 0, TILE_SIZE, TILE_SIZE, null);
                }
                // wins
                else if (currentWalls[y][x] == 2) {
                    g.drawImage(winActiveImage.getImage(), TILE_SIZE + (int)(x * TILE_SIZE), TILE_SIZE + (int)(y * TILE_SIZE), TILE_SIZE * 2, TILE_SIZE * 2, null);
                }
            }
        }

        // places the boxes on the edges
        for (int i = 0; i < currentWalls.length + 2; i++) {
            for (int j = 0; j < currentWalls.length + 2; j++) {
                if (i == 0 || j == 0 || i == currentWalls.length + 1 || j == currentWalls.length + 1) {
                    g.drawImage(wallImg.getImage(), i * TILE_SIZE, j * TILE_SIZE + 0, TILE_SIZE, TILE_SIZE, null);
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

        altImages = !altImages;

        // if player inside wall, reset player position
        if (currentWalls[playerY][playerX] == 1) {
            ResetPlayerPosition();
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

    private void PrepareLevel(String input) {
        // player starting X
        int startX = 0;

        int i = 0;
        while(input.charAt(i) != ',') {
            int currentNumber = input.charAt(i) - '0';
            
            startX *= 10;
            startX += currentNumber;
            i++;
        }

        input = input.substring(i + 1);

        // player starting Y
        int startY = 0;

        i = 0;
        while(input.charAt(i) != ',') {
            int currentNumber = input.charAt(i) - '0';
            
            startY *= 10;
            startY += currentNumber;
            i++;
        }

        input = input.substring(i + 1);


        int[] playerStartPos = new int[2];
        playerStartPos[0] = startX;
        playerStartPos[1] = startY;
        playerData.add(playerStartPos);


        int[][] level = new int[13][13];
        for (int[] row : level) {
            Arrays.fill(row, 0);
        }
        
        for (i = 0; i < input.length() && i < (13 * 13 * 2); i++) {

            int currentNumber = input.charAt(i) - '0';

            if (currentNumber >= 0 && currentNumber <= 2) {
                level[i % (13 * 13) / 13][i % 13] = currentNumber;
            } else {
                level[i % (13 * 13) / 13][i % 13] = 0;
            }
            System.out.print(input.charAt(i));

            if (i == 13 * 13 - 1) {
                levelData.add(level);
                System.out.println("level added");
                level = new int[13][13];
                for (int[] row : level) {
                    Arrays.fill(row, 0);
                }
            }

            if (i == 13 * 13 * 2 - 1) {
                levelData.add(level);
                i = input.length();

                if (input.indexOf('-') >= 0) {
                    PrepareLevel(input.substring(input.indexOf('-')));
                }
            }
        }
        System.out.println("");
    }

    private void LoadNextLevel(){
        if (currentLevel + 1 < levelData.size() / 2) {
            LoadLevel(currentLevel + 1);
        } else {
            System.out.println("Game finished yayayayayya!");
        }
    }

    private void LoadLevel(int levelNum){
        System.out.println("level data size is " + levelData.size());
        if (levelData.size() / 2 > levelNum) {
            wallsOne = levelData.get(levelNum * 2);
            wallsTwo = levelData.get(levelNum * 2 + 1);

            currentWalls = wallsOne;
            altWalls = wallsTwo;

            altImages = false;

            playerStartX = playerData.get(levelNum)[0];
            playerStartY = playerData.get(levelNum)[1];

            playerX = playerStartX;
            playerY = playerStartY;

            currentLevel = levelNum;
        } else {
            System.out.println("Attempting to load a level that doesn't exist!");
        }
    }

    // Luckyzelle
    // class for detecting key presses
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (canMove) {
                if ((key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER)) {
                    System.out.println("space / enter");
                    swapWalls();
                    canMove = false;
                }

                if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)) {
                    System.out.println("left");
                    calculateNextPlayerPosition(-1, 0);
                    canMove = false;
                }

                if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)) {
                    System.out.println("right");
                    calculateNextPlayerPosition(1, 0);
                    canMove = false;
                }

                if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W)) {
                    System.out.println("up");
                    calculateNextPlayerPosition(0, -1);
                    canMove = false;
                }

                if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)) {
                    System.out.println("down");
                    calculateNextPlayerPosition(0, 1);
                    canMove = false;
                }
            }
        }
    }
}