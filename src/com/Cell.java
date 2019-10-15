package com;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.Objects;

/** Store the value previousCost(the distance from the started cell to this cell), predictedCost(heuristic value?) and selfCost
 *
 */
public class Cell extends BorderPane {
     int x;
     int y;
    private Enum type;
    private int previousCost;
    private int predictedCost;
    private int selfCost;

    private String name;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public Cell(int x, int y, Cell.TYPE type) {
        this.x = x;
        this.y = y;
        setType(type);

    }
    public Cell( String name, int x, int y, Cell.TYPE type){
        this.x = x;
        this.y = y;
        this.name = name;
        setType(type);

    }

    public int getSelfCost() {
        return selfCost;
    }

    public void setSelfCost(int selfCost) {
        this.selfCost = selfCost;
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

    public void setType(Cell.TYPE type) {

        setTop(new Label("(" + x + ", " + y + ")"));
        setCenter(new Label(type.name().toLowerCase()));

        //remove existing type
        getStyleClass().clear();
        getStyleClass().add("cell");

        this.type = type;

        switch (type) {
            case START:
                setSelfCost(1);
                getStyleClass().add("start");
                break;
            case BLOCKED:
                // TODO: Implement blocked self cost. (Infinity?)
                getStyleClass().add("block");
                break;
            case END:
                setSelfCost(1);
                getStyleClass().add("end");
                break;
            case PATH:
                getStyleClass().add("path");
                break;
            default:
                setSelfCost(1);
                getStyleClass().add("normal");
        }
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
        NORMAL, BLOCKED, DEADEND,START,END,PATH;
    }




}
