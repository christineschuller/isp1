package com.isp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

// Class AStar implements the actual search-algorithm
public class AStar {

    final Board board; // the search field

    Cell startCell; // the cell from where you want the path
    Cell targetCell; // the target where to you want your path to go
    Cell currentCell; // the cell where you are right now

    List<Cell> path = new ArrayList<>(); // the future output of our path

    public AStar(Board board) {
        this.board = board;
    }

    public void setStartCell(int x, int y) { // setting the start cell, which can be defined by ourselves
        startCell = board.get(x, y);
        startCell.setPredictedCost(0);

        currentCell = startCell;
        path.add(startCell);
    }

    public void setTargetCell(int x, int y) { // setting the target cell, which can be defined by ourselves
        targetCell = board.get(x, y);
        targetCell.setPredictedCost(0);
    }

    public void setPredictedCosts(Board board) { // calculating and setting the predicted cost of each cell
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                // Manhattan Heuristic
                //board.get(i, j).predictedCost = Math.abs(currentCell.getX() - targetCell.getX()) + Math.abs(currentCell.getY() - targetCell.getY());
                board.get(i, j).predictedCost = Math.abs(board.get(i, j).getX() - targetCell.getX()) + Math.abs(board.get(i, j).getX() - targetCell.getY());
                //Diagonal
                //Euclidean Heuristic
            }
        }
    }

    public List<Cell> getPath() {
        boolean hasOptions = true;
        List<Cell> visited = new ArrayList<>();
        visited.add(currentCell);
        while (!currentCell.equals(targetCell) && hasOptions) {
            List<Cell> neighbours = new ArrayList<>();
            Cell neighbour = board.get(currentCell.getX(), currentCell.getY() + 1);
           // neighbour.setPreviousCost(path.size());
            if (neighbour != null && !path.contains(neighbour) && neighbour.getType() == Cell.TYPE.NORMAL && !visited.contains(neighbour) && !neighbour.equals(targetCell)) {
                neighbours.add(neighbour);
                visited.add(neighbour);
            }else neighbour=targetCell;

            neighbour = board.get(currentCell.getX() + 1, currentCell.getY());


            if (neighbour != null && !path.contains(neighbour) && neighbour.getType() == Cell.TYPE.NORMAL && !visited.contains(neighbour) && !neighbour.equals(targetCell)) {
                neighbours.add(neighbour);
                visited.add(neighbour);
            }else neighbour=targetCell;

            neighbour = board.get(currentCell.getX() - 1, currentCell.getY());
            // neighbour.setPreviousCost(path.size());

            if (neighbour != null && !path.contains(neighbour) && neighbour.getType() == Cell.TYPE.NORMAL && !visited.contains(neighbour) && !neighbour.equals(targetCell)) {
                neighbours.add(neighbour);
                visited.add(neighbour);
            }else neighbour=targetCell;

            neighbour = board.get(currentCell.getX(), currentCell.getY() - 1);
           // neighbour.setPreviousCost(path.size());

            if (neighbour != null && !path.contains(neighbour) && neighbour.getType() == Cell.TYPE.NORMAL && !visited.contains(neighbour) && !neighbour.equals(targetCell)) {
                neighbours.add(neighbour);
                visited.add(neighbour);
            }else neighbour=targetCell;

            // TODO: Select best option
            neighbours.sort((a, b) -> b.getTotalCost() - a.getTotalCost());

            // TODO: Add option to path
            if (hasOptions = neighbours.size() > 0) {
                Cell bestNeighbour = neighbours.get(0);
                int bestCost = bestNeighbour.getTotalCost();
                List<Cell> bestCostOptions = neighbours.stream().filter(e -> e.getTotalCost() == bestCost).collect(Collectors.toList());
                //path.add(bestCostOptions.get(rand.nextInt(bestCostOptions.size())));
                //Cell oneRandomFromBestOptions = .getRandomElement(bestCostOptions);
                Random rand = new Random();
                Cell randomBestCellFromList = bestCostOptions.get(rand.nextInt(bestCostOptions.size()));
                path.add(randomBestCellFromList);
                currentCell = randomBestCellFromList;
                currentCell.setPreviousCost(currentCell.previousCost++);
                //hasOptions=false;
            }


            //currentCell = path.get(path.size() - 1);
        }
        // TO DO: Make a step backwards if the cell has no options ( return to parent cell and choose the next best option ). Create a new Type NOFURTHERWAY ( es gibt ein Unterschied zwischen Blocked und Nofurtherway )
        return path;
    }
}
