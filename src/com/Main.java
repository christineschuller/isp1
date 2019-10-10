package com;

import java.util.List;

import input.MouseDragGestures;
import input.MousePaintGestures;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;



public class Main {

        Board board = new Board(6,6);
        AStar astar = new AStar(board);

        MousePaintGestures mousePaint = new MousePaintGestures();
        MouseDragGestures mouseDrag;

        boolean autoPath = true;
        boolean showSteps = true;

        Cell start;
        Cell end;

        public void setUp(Stage stage){
            try {
                BorderPane root = new BorderPane();


                //placeholder for the board
                StackPane content = new StackPane();
                root.setCenter(content);

                //Toolbar for the button
                HBox toolbar = new HBox();
                toolbar.setAlignment(Pos.CENTER);
                toolbar.setPadding(new Insets(20,20,20,20));
                toolbar.setSpacing(5);


            }catch (Exception e){
                e.printStackTrace();
            }
        }






        public static void main(String[] args) {
            Board board = new Board(4, 4);
            AStar astar = new AStar(board);

            board.get(0, 1).setType(Cell.TYPE.BLOCKED);
            board.get(2, 1).setType(Cell.TYPE.BLOCKED);
            board.get(1, 2).setType(Cell.TYPE.BLOCKED);

            astar.setStart(0, 0);
            astar.setTarget(3, 3);

            List<Cell> path = astar.getPath();
            List<Cell> path2 = astar.getPath();
            List<Cell> path3 = astar.getPath();

            AStar.printPath(path);
//            AStar.printPath(path2);
//            AStar.printPath(path3);
        }
    }


