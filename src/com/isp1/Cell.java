package com.isp1;

import javax.swing.*;

public class Cell extends JPanel {
    int x;
    int y;

    int previousCost;
    int predictedCost;
    int totalCost;

    Cell parent;

    public Cell(int index_x, int index_y) {
        this.x = index_x;
        this.y = index_y;
        //this.parent = parent;
        //this.predictedCost = calculatePredictedCost();
        //this.previousCost = calculatePreviousCost();
        //this.totalCost = predictedCost + previousCost;
    }
}
