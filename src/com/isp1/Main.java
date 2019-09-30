package com.isp1;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(4,4);
        AStar astar = new AStar(board);
        astar.setStartCell(0,0);
        astar.setTargetCell(3,3);
        System.out.println( Math.abs(astar.startCell.getX() - astar.targetCell.getX()) + Math.abs(astar.startCell.getY() - astar.targetCell.getY()));

       astar.setPredictedCosts(board);

        List<Cell> path = astar.getPath();

        for (Cell c : path) System.out.println(c);
    }
}
