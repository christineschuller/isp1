package com.isp1;

import javax.swing.*;
import java.util.Objects;

public class Cell extends JPanel {
    private final int x;
    private final int y;
    private final Enum type;

    private int previousCost;
    private int predictedCost;
    private int totalCost;

    public Cell(int x, int y, Enum type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Enum getType() {
        return this.type;
    }

    public int getPreviousCost() {
        return previousCost;
    }

    public void setPreviousCost(int previousCost) {
        this.previousCost = previousCost;
    }

    public int getPredictedCost() {
        return predictedCost;
    }

    public void setPredictedCost(int predictedCost) {
        this.predictedCost = predictedCost;
    }

    public int getTotalCost() {
        return previousCost + predictedCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x &&
                y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public enum TYPE {
        NORMAL, BLOCKED;
    }
}
