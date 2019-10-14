package com.isp1;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(4, 4);
        AStar astar = new AStar(board);

        board.get(0, 1).setType(Cell.TYPE.BLOCKED);
        board.get(1, 0).setType(Cell.TYPE.BLOCKED);
        board.get(1, 1).setType(Cell.TYPE.BLOCKED);
        board.get(2, 2).setType(Cell.TYPE.BLOCKED);

       // board.get(2, 1).setType(Cell.TYPE.BLOCKED);
       // board.get(3, 1).setType(Cell.TYPE.BLOCKED);

        astar.setStart(0, 0);
        astar.setTarget(3, 3);

        List<Cell> path = astar.getPath();
       // List<Cell> path2 = astar.getPath();
        //List<Cell> path3 = astar.getPath();

        AStar.printPath(path);
       // AStar.printPath(path2);
       // AStar.printPath(path3);

    }
}
