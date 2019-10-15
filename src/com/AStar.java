package com;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

// Class AStar implements the actual search-algorithm
public class AStar {

    private final Board board; // the search field
    private Cell start; // the cell from where you want the path
    private Cell target; // the target where to you want your path to go
    private Heuristic heuristic;

    public AStar(Board board) {
        this.board = board;
        this.heuristic = new Manhattan();
    }

    public void setStart(Cell start) {
        this.start = start;
    }

    public void setStart(int x, int y) {
        this.start = board.get(x, y);
        this.start.setPreviousCost(0);
    }

    public void setTarget(Cell target) {
        this.target = target;
    }

    public void setTarget(int x, int y) {
        this.target = board.get(x, y);
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    private void setPredictedCosts() { // calculating and setting the predicted cost of each cell
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                board.get(i, j).setPredictedCost(heuristic.getCost(board.get(i, j), target));
            }
        }
    }

    private void updatePreviousCost(List<Cell> path, Cell cell) {
        Cell parent = path.get(path.size() - 1);
        int previousCost = parent != null ? parent.getPreviousCost() : 0;
        cell.setPreviousCost(previousCost + cell.getSelfCost());
    }

    private boolean neighbourSanityCheck(List<Cell> path, Cell neighbour) {
        return neighbour != null && !path.contains(neighbour) && neighbour.getType() != Cell.TYPE.BLOCKED;
    }

    public List<Cell> getPath() {
        setPredictedCosts();

        Cell current = start;

        List<Cell> path = new ArrayList<>();
        path.add(current);

        boolean problem = false;
        while (!problem && !current.equals(target) && current.getType() != Cell.TYPE.DEADEND) {
            List<Cell> neighbours = new ArrayList<>();

            Cell neighbour = board.get(current.getX(), current.getY() + 1);
            if (neighbourSanityCheck(path, neighbour)) {
                neighbours.add(neighbour);
                updatePreviousCost(path, neighbour);
            }

            neighbour = board.get(current.getX() + 1, current.getY());
            if (neighbourSanityCheck(path, neighbour)) {
                neighbours.add(neighbour);
                updatePreviousCost(path, neighbour);
            }

            neighbour = board.get(current.getX() - 1, current.getY());
            if (neighbourSanityCheck(path, neighbour)) {
                neighbours.add(neighbour);
                updatePreviousCost(path, neighbour);
            }

            neighbour = board.get(current.getX(), current.getY() - 1);
            if (neighbourSanityCheck(path, neighbour)) {
                neighbours.add(neighbour);
                updatePreviousCost(path, neighbour);
            }

            if (neighbours.contains(target)) {
                current = target;
                path.add(current);
                continue;
            }

            neighbours.sort(Comparator.comparingInt(Cell::getTotalCost));

            if (neighbours.size() > 0) {
                Cell bestNeighbour = neighbours.get(0);
                int bestCost = bestNeighbour.getTotalCost();
                List<Cell> bestCostOptions = neighbours.stream().filter(e -> e.getTotalCost() == bestCost).collect(Collectors.toList());
                Random rand = new Random();
                current = bestCostOptions.get(rand.nextInt(bestCostOptions.size()));
                path.add(current);
            } else {
                problem = true;
            }
        }
        // TODO: Make a step backwards if the cell has no options ( return to parent cell and choose the next best option ). Create a new Type NOFURTHERWAY ( es gibt ein Unterschied zwischen Blocked und Nofurtherway )
        return path;
    }

    public static void printPath(List<Cell> path) {
        System.out.print("Path: ");
        for (int i = 0; i < path.size(); i++) {
            Cell c = path.get(i);
            System.out.print("(" + c.getX() + "," + c.getY() + ")");
            if (i < path.size() - 1) System.out.print(" => ");
        }
        System.out.println();
    }
}

