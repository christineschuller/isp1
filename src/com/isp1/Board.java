package com.isp1;

public class Board {
    private final int width;
    private final int height;
    private final Cell[][] board;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new Cell[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = new Cell(x, y, Cell.TYPE.NORMAL);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public Cell get(int x, int y) {
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1) return null;
        return board[x][y];
    }
}
