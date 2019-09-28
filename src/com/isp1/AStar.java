package com.isp1;

import java.util.PriorityQueue;

import static java.lang.StrictMath.abs;

public class AStar {
    Cell startCell;
    Cell targetCell;

    int startCellx;
    int startCelly;

    int targetCellx;
    int targetCelly;

    boolean[][] visitedCells;
    PriorityQueue<Cell> unvisitedCells;
    boolean[][] blocked;
    Cell[][] matrixOfCells;

    public void setBlocked(int x, int y) {
        matrixOfCells[x][y] = null;
        visitedCells[x][y] = true;
    }

    public void setStartCell(int x, int y) {
        startCell = new Cell(x, y);
        matrixOfCells[x][y] = startCell;
        startCellx = startCell.x;
        startCelly = startCell.y;
        startCell.previousCost = 0;
        startCell.predictedCost = abs(startCellx - targetCellx) + abs(startCelly - targetCelly);
        startCell.totalCost = startCell.predictedCost + startCell.previousCost;
        visitedCells[startCellx][startCelly] = false;
        unvisitedCells.add(startCell);
    }

    public void setTargetCell(int x, int y) {
        targetCell = new Cell(x, y);
        matrixOfCells[x][y] = targetCell;
        targetCellx = targetCell.x;
        targetCelly = targetCell.y;
        targetCell.predictedCost = 0;
        visitedCells[targetCellx][targetCelly] = false;
        unvisitedCells.add(targetCell);
    }


    public void compareCells(Cell currentCell, Cell neighbourCell) {
        visitedCells[currentCell.x][currentCell.y] = true;
        unvisitedCells.remove(currentCell);

        if (neighbourCell == null || visitedCells[neighbourCell.x][neighbourCell.y] == true || blocked[neighbourCell.x][neighbourCell.y] == true) {
            return;
        }

        //neighbourCell.totalCost= currentCell.previousCost + neighbourCell.predictedCost;

        if (unvisitedCells.contains(neighbourCell)) {
            neighbourCell.totalCost = currentCell.previousCost + neighbourCell.predictedCost;
            unvisitedCells.remove(neighbourCell);
            visitedCells[neighbourCell.x][neighbourCell.y] = true;
        }
    }

    public void findPathAlgorithm() {
        // TODO: findPathAlgorithm
    }
}
