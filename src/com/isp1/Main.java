package com.isp1;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(5,5);
        AStar astar = new AStar(board);
        astar.setStartCell(0,0);
        astar.setTargetCell(4,4);
        List<Cell> path = astar.getPath();

        for (Cell c : path) System.out.println(c);
    }
}
