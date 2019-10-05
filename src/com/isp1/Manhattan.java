package com.isp1;

public class Manhattan implements Heuristic {
    @Override
    public int getCost(Cell current, Cell target) {
        return Math.abs(current.getX() - target.getX()) + Math.abs(current.getY() - target.getY());
    }
}
