package org.example;

public class GameLogic extends Game_of_life{
    public static Tile[][] board;

    GameLogic(int boardWidth, int boardHeight) {
        super(boardWidth, boardHeight);
    }

    public static void initialize(Tile[][] initialBoard) {
        board = initialBoard;
    }

    public static void born() {
        Tile[][] newBoard = new Tile[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);

                // Apply rules
                if (board[i][j].alive) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        newBoard[i][j] = new Tile(board[i][j].x, board[i][j].y, false); // Die
                    } else {
                        newBoard[i][j] = new Tile(board[i][j].x, board[i][j].y, true); // Survive
                    }
                } else {
                    if (liveNeighbors == 3) {
                        newBoard[i][j] = new Tile(board[i][j].x, board[i][j].y, true); // Born
                    } else {
                        newBoard[i][j] = new Tile(board[i][j].x, board[i][j].y, false); // Stay dead
                    }
                }
            }
        }

        board = newBoard;
    }

    private static int countLiveNeighbors(int x, int y) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighborX = x + i;
                int neighborY = y + j;

                if (neighborX >= 0 && neighborX < board.length && neighborY >= 0 && neighborY < board[0].length) {
                    if (board[neighborX][neighborY].alive && !(i == 0 && j == 0)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
