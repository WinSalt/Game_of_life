package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Game_of_life extends JPanel implements ActionListener{

    public static class Tile {
        boolean alive;
        int x;
        int y;

        //private boolean alive;

        Tile(int x, int y, boolean alive) {
            this.x = x;
            this.y = y;
            this.alive = alive;
        }
    }

    Random generator = new Random();
    int boardWidth;
    int boardHeight;
    int tileSize = 15;
    final int startBlocks = 360;

    Timer gameLoop;

    //Tile GameTile;
    //LinkedList<Tile> list_of_blocks = new LinkedList<Tile>();
    public Tile[][] list_of_blocks;

    Game_of_life(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        list_of_blocks = new Tile[boardWidth/tileSize][boardHeight/tileSize];
        Start_board();

        GameLogic.initialize(list_of_blocks);

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    private void Start_board() {
            int coord_x = 0;
            int coord_y = 0;
            int rand_x;
            int rand_y;

            // All tiles dead
            for(int i = 0; i < list_of_blocks.length; i++) {
                for (int j = 0; j < list_of_blocks[0].length; j++) {
                    list_of_blocks[i][j] = new Tile(coord_x, coord_y, false);
                    coord_y += tileSize;
                }
                coord_x += tileSize;
                coord_y = 0;
            }

            // Random choose tiles to be alive
            for (int i = 0; i < startBlocks; i++) {
                rand_x = generator.nextInt(boardWidth / tileSize);
                rand_y = generator.nextInt(boardWidth / tileSize);
                list_of_blocks[rand_x][rand_y].alive = true;
            }

        }


        /*for (int i = 0; i < startBlocks; i++) {
            list_of_blocks.add(new Tile(generator.nextInt(boardWidth / tileSize), generator.nextInt(boardHeight / tileSize), false));
        }

        for (int i = 0; i < startBlocks; i++) {
            if (list_of_blocks.get(i).alive == false) {

            } else {
                list_of_blocks.add(new Tile(generator.nextInt(boardWidth / tileSize), generator.nextInt(boardHeight / tileSize), true));
            }
        }*/

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw_grid(g);
        draw_blocks(g);
    }

    public void draw_grid(Graphics g) {
        //Grid
        for (int i = 0; i < boardWidth/tileSize; i++) {
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        }
    }

    public void draw_blocks(Graphics g) {
        //Block
        for (int i = 0; i < list_of_blocks.length; i++) {
            for (int j = 0; j < list_of_blocks[0].length; j++) {
                if(list_of_blocks[i][j].alive) {
                    g.setColor(Color.yellow);
                    g.fillRect(list_of_blocks[i][j].x, list_of_blocks[i][j].y, tileSize, tileSize);
                }
                else {
                    g.setColor(Color.blue);
                }
                //g.fillRect(list_of_blocks[i][j].x, list_of_blocks[i][j].y, tileSize, tileSize);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        list_of_blocks = GameLogic.board;
        GameLogic.born(); // Apply Game of Life rules
        repaint();
    }
}
