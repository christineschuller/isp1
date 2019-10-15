package com;

import java.util.NoSuchElementException;

public class Manhattan implements Heuristic {
    @Override
    public int getCost(Cell current, Cell target) {
        if(current == null & target == null ) throw new NoSuchElementException();
        return Math.abs(current.getX() - target.getX()) + Math.abs(current.getY() - target.getY());
    }
}
