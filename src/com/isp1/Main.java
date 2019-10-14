package com.isp1;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(4, 4);
        AStar astar = new AStar(board);

        board.get(0, 1).setType(Cell.TYPE.BLOCKED);
        board.get(1, 0).setType(Cell.TYPE.BLOCKED);
        // board.get(1, 1).setType(Cell.TYPE.BLOCKED);
        board.get(2, 2).setType(Cell.TYPE.BLOCKED);

        // board.get(2, 1).setType(Cell.TYPE.BLOCKED);
        // board.get(3, 1).setType(Cell.TYPE.BLOCKED);

        astar.setStart(0, 0);
        astar.setTarget(2, 3);

        // if (astar == null) throw new NoSuchElementException();
        List<Cell> path = astar.getPath();
        // List<Cell> path2 = astar.getPath();
        //List<Cell> path3 = astar.getPath();

        AStar.printPath(path);
        // AStar.printPath(path2);
        // AStar.printPath(path3);
        String[][] consoleOutput = new String[board.getWidth()][board.getWidth()];
        System.out.println("----------------------------------------------");

        for (int k = 0; k < consoleOutput.length; k++) {
            for (int l = 0; l < consoleOutput[k].length; l++) {
                if (path.contains(board.get(k, l))) {
                    System.out.print("   "+ k + "," + l + "    |");
                } else if (board.get(k, l).getType() == Cell.TYPE.BLOCKED) System.out.print(" blocked  |");
                else System.out.print("  normal  |");

            }
            if(k<consoleOutput.length-1) System.out.println();

        }
        System.out.println();
        System.out.println("----------------------------------------------");

    }
}
