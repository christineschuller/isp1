package com.isp1;

public class DiagonalHeuristic implements Heuristic {
    @Override
    public int getCost(Cell current, Cell target) {
       /* function heuristic(node) =
                dx = abs(node.x - goal.x)
        dy = abs(node.y - goal.y)
        return D * (dx + dy) + (D2 - 2 * D) * min(dx, dy)*/
        int value1 = Math.abs(current.getX()-target.getX());
        int value2 = Math.abs(current.getY()-target.getY());

        if(value1<value2) return value2;
        else return value1;
    }
}
