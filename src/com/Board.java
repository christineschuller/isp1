package com;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class Board extends Pane {
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

    public static void printBoard(Board board) {
        for (Cell[] row : board.getBoard()) {
            for (Cell cell : row) {
                System.out.printf("| (%d,%d) g: %02d, h: %02d ",
                        cell.getX(), cell.getY(), cell.getPreviousCost(), cell.getPredictedCost());
            }
            System.out.println("|");
        }
    }

/*    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }*/

    public Cell[][] getBoard() {
        return board;
    }

    public Cell get(int x, int y) {
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1) return null;
        return board[x][y];
    }

    public void set(int x, int y, Cell cell) {
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1) {
            System.out.println("THIS PLACE DOES NOT EXISTS");
        } else {
            board[x][y] = cell;
        }
        getChildren().add(cell);
    }

    /**
     * Change cell type of all types to default type.
     */
    public void setType( Cell.TYPE type) {

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                board[row][col].setType( type);
            }
        }
    }

    /**
     * Ensure the overlay cell is exactly over a grid cell
     * @param overlayCell
     */
    public void snapToGrid( Cell overlayCell) {

        double centerX = overlayCell.getBoundsInParent().getMinX() + overlayCell.getBoundsInParent().getWidth() / 2;
        double centerY = overlayCell.getBoundsInParent().getMinY() + overlayCell.getBoundsInParent().getHeight() / 2;

        Point2D centerPoint = new Point2D( centerX, centerY);

        boolean found = false;

        for (int row = 0; row < board.length; row++) {

            for (int col = 0; col < board[row].length; col++) {

                Cell gridCell = board[row][col];

                if( gridCell.getBoundsInParent().contains( centerPoint)) {

                    overlayCell.setTranslateX(0);
                    overlayCell.setTranslateY(0);

                    overlayCell.setLayoutX( gridCell.getLayoutX());
                    overlayCell.setLayoutY( gridCell.getLayoutY());

                    overlayCell.x = gridCell.x;
                    overlayCell.y = gridCell.y;

                    found = true;

                }

                if( found) {
                    break;
                }
            }

            if( found) {
                break;
            }
        }
    }
}
