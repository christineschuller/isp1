package com.isp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AStar {
    final Board board;

    Cell startCell;
    Cell targetCell;
    Cell currentCell;

    List<Cell> path = new ArrayList<>();

    public AStar(Board board) {
        this.board = board;
    }

    public void setStartCell(int x, int y) {
        startCell = board.get(x, y);
        startCell.setPredictedCost(0);

        currentCell = startCell;
        path.add(startCell);
    }

    public void setTargetCell(int x, int y) {
        targetCell = board.get(x, y);
        targetCell.setPredictedCost(0);
    }
    public void setPredictedCosts(Board board){
        for(int i=0;i<board.getWidth();i++){
            for(int j=0;j<board.getHeight();j++){
                //return Math.abs(this.now.x + dx - this.xend) + Math.abs(this.now.y + dy - this.yend);
                board.get(i,j).setPredictedCost(Math.abs(currentCell.getX()-targetCell.getX())+Math.abs(currentCell.getY()-targetCell.getY()));
            }
        }
    }

    public List<Cell> getPath() {
        boolean hasOptions = true;
        while (!currentCell.equals(targetCell) && hasOptions) {
            List<Cell> neighbours = new ArrayList<>();
            Cell neighbour = board.get(currentCell.getX(), currentCell.getY() + 1);
            if (neighbour != null && !path.contains(neighbour) && neighbour.getType() == Cell.TYPE.NORMAL) {
                neighbours.add(neighbour);
            }

            neighbour = board.get(currentCell.getX() + 1, currentCell.getY());
            if (neighbour != null && !path.contains(neighbour) && neighbour.getType() == Cell.TYPE.NORMAL) {
                neighbours.add(neighbour);
            }

            neighbour = board.get(currentCell.getX() - 1, currentCell.getY());
            if (neighbour != null && !path.contains(neighbour) && neighbour.getType() == Cell.TYPE.NORMAL) {
                neighbours.add(neighbour);
            }

            neighbour = board.get(currentCell.getX(), currentCell.getY() - 1);
            if (neighbour != null && !path.contains(neighbour) && neighbour.getType() == Cell.TYPE.NORMAL) {
                neighbours.add(neighbour);
            }

            // TODO: Select best option
            neighbours.sort((a, b) -> b.getTotalCost() - a.getTotalCost());
            // TODO: Add option to path
            if (hasOptions = neighbours.size() > 0) {
                Cell bestNeighbour = neighbours.get(0);
                int bestCost = bestNeighbour.getTotalCost();
                List<Cell> bestCostOptions = neighbours.stream().filter(e -> e.getTotalCost() == bestCost).collect(Collectors.toList());
                Random rand = new Random();
                path.add(bestCostOptions.get(rand.nextInt(bestCostOptions.size())));
            }

            currentCell = path.get(path.size() - 1);
        }
        return path;
    }
}
