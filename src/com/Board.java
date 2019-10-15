package com;

import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.NoSuchElementException;

public class Board extends GridPane {
    public final int cols;
    public final int rows;
    private final Cell[][] board;

    public Board(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        this.board = new Cell[cols][rows];

        //generate later
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                board[x][y] = new Cell(x, y, Cell.TYPE.NORMAL);
                this.add(board[x][y],x,y);
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
    /** Gibt eine Spielfeldzelle zurück.
     * @param x Spaltenindex (horizontal).
     * @param y Zeilenindex (vertikal).
     * @return Referenz auf die Zelle oder 'null' bei ungültigem Index. */

    public Cell getCell(int x, int y) {
        if (x < 0 || y < 0 || x >= board.length || y >= board[0].length){
            //doing nothing
        };
        return board[x][y];
    }


    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public Cell get(int x, int y) {
        if (x < 0 || x > cols - 1 || y < 0 || y > rows - 1) return null;
        return board[x][y];
    }

    public void set(int x, int y, Cell cell) {
        if (x < 0 || x > cols - 1 || y < 0 || y > rows - 1) {
            System.out.println("THIS PLACE DOES NOT EXISTS");
        } else {
            board[x][y] = cell;
        }


    }

    /**
     * Change cell type of all types to default type.
     */
    public void setType( Cell.TYPE type) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col].getStyleClass().clear();
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
