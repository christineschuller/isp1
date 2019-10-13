import java.util.List;

import com.AStar;
import com.Board;
import com.Cell;
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

import static javafx.application.Application.launch;


public class Main extends Application{

    public class Settings {

        // fullscreen or windowed mode
        public boolean STAGE_FULLSCREEN = false;
        public double STAGE_FULLSCREEN_SCALE = 1;

        public double SCENE_WIDTH = 1280;
        public double SCENE_HEIGHT = 720;

        public  int GRID_ROWS = 9;
        public  int GRID_COLUMNS = 18;

        public boolean STEP_VIEW = false;
    }
        /*
        ====== SETTING THE BOARD ==============
         */
        Board board = new Board(10,10);
        AStar astar = new AStar(board);



         /*
           ====== CONSTANTS ==============
            */
       // BooleanProperty stepViewProperty = new SimpleBooleanProperty( false);
        BooleanProperty showPathProperty = new SimpleBooleanProperty( true);

        MousePaintGestures mousePaint = new MousePaintGestures();
        MouseDragGestures mouseDrag;

        private boolean autoPath = true;
        //private boolean showSteps = true;

        Cell start;
        Cell end;

        Label status;
        //Slider slider;

        @Override
        public void start(Stage stage){
            try {
                BorderPane root = new BorderPane();

                //placeholder for the board
                StackPane boardHolder = new StackPane();
                root.setCenter(boardHolder);

                //Toolbar for the button and checkboxes

                HBox toolbar = new HBox();
                toolbar.setAlignment(Pos.CENTER);
                toolbar.setPadding(new Insets(10,10,10,10));
                toolbar.setSpacing(5);


                // remove block from the board
                Button removeBlocksButton = new Button("Remove Blocks");

                removeBlocksButton.setOnAction(event -> {
                    board.setType(Cell.TYPE.NORMAL);
                     if(autoPath){
                         showWay();
                     }
                });

                // generate block
                //TODO
                //

                //find Path Button
                Button findPathButton = new Button("Find Way");
                findPathButton.setOnAction(event -> {
                    showWay();
                });

                //show-hide path
                CheckBox showPathCheckBox = new CheckBox("Show Way");
                showPathCheckBox.selectedProperty().bindBidirectional(
                showPathProperty);
                showPathProperty.addListener((ChangeListener<Boolean>) ((observable, oldValue, newValue) -> {
                            showWay();
                    }
                    ));

                //Allow diagonals
                //TODO
                //

 /*               //steps need info label
                Label stepLabel = new Label();

                slider = new Slider();
                slider.setMin(0);
                slider.setMax(0);
                slider.setValue(0);
                slider.setShowTickLabels(true);
                slider.setShowTickMarks(true);
                slider.setBlockIncrement(1);
                slider.setPrefWidth(300);
                slider.valueProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        stepLabel.setText("test" + newValue.intValue());
                        showWay();
                    }
                });*/

                //===============================

                toolbar.setAlignment(Pos.CENTER);

                // Set the element in the toolbar
                toolbar.getChildren().addAll(removeBlocksButton,findPathButton,showPathCheckBox);

                root.setTop(toolbar);

                //status
                HBox statusBar = new HBox();
                status = new Label();
                statusBar.getChildren().add(status);

                //info to use
                Label infoText = new Label("Left mouse button = draw block, right mouse button = remove block.");

                infoText.setAlignment(Pos.CENTER);
                infoText.setMaxWidth(Long.MAX_VALUE);
                HBox.setHgrow(infoText, Priority.ALWAYS);
                statusBar.getChildren().add( infoText);

                root.setBottom(statusBar);



                //Generate Scence
                Scene scene = new Scene(root,board.getWidth(),board.getHeight());


                scene.getStylesheets().add(getClass().getResource("style/application.css").toExternalForm());

                stage.setScene(scene);
                stage.setTitle("A*Algo Implement");

                stage.show();

                //default setting
                //stage.setFullScreen(true);

                /*
                Create the Board =======================================
                 */
                board = new Board(100,100);

                // fill the board with cells
                for(int row =0; row < board.getHeight();row++){
                    for(int col = 0; col < board.getWidth(); col++){
                        Cell cell = new Cell(col,row,Cell.TYPE.NORMAL);
                        cell.getStyleClass().add("");
                        mousePaint.makePaintable(cell);
                        board.set(col,row,cell);
                    }
                }
                root.setCenter(board);

                /*
                EXAMPLE ==============================================
                 */

                start = new Cell(0,0,Cell.TYPE.NORMAL);
                end = new Cell(5,5,Cell.TYPE.NORMAL);

                //apply style on the cell
                start.getStyleClass().add("start");
                end.getStyleClass().add("end");

                start.toFront();
                end.toFront();

                mouseDrag = new MouseDragGestures(board);
                mouseDrag.makeDragable(start);
                mouseDrag.makeDragable(end);

                // redraw the path after release the mouse
                board.addEventFilter(MouseEvent.MOUSE_RELEASED,onMouseReleased);
                start.addEventFilter(MouseEvent.MOUSE_RELEASED,onMouseReleased);
                end.addEventFilter(MouseEvent.MOUSE_RELEASED,onMouseReleased);


            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /*
        Set up default to show the path
         */
        EventHandler<MouseEvent> onMouseReleased = event -> {
          if(autoPath){
              showWay();
          }
        };
        /*
        Search for the path and show if requested
         */
        public void showWay(){
            findWay(board.get(start.getX(),start.getY()),board.get(end.getX(),end.getY()));
        }

    /**
     * Remove Paint
     */
        public void removePaint() {
            for (int row = 0; row < board.getHeight(); row++) {
                for (int col = 0; col < board.getWidth(); col++) {
                    board.get(row,col).getStyleClass().remove("path");
                }
            }
        }
        /*

         */
        private void findWay(Cell start, Cell target){
            // generate a Path through the A* Algo
            astar.setStart(start);
            astar.setTarget(target);
            List<Cell> path = astar.getPath();
            String context;
            if(path!= null){
                context = "Cell in Path: "+ path.size();
            }else{
                context = "No Path to be found";
            }
            status.setText(context);

            //paint the path
            paintWay(path);

        }
         /*
            Paint the way from the start to the target
         */
        private void paintWay(List<Cell> path){
            //remove all paint at first
            removePaint();

            //paint the way
            if(path!= null){
                //paint the way
                for(Cell cell : path){
                    cell.getStyleClass().add("path");
                }
            }else{
                System.out.println("NO WAY IS TO BE FOUND !");
            }
        }












        public static void main(String[] args) {
            launch(args);

            /*Board board = new Board(4, 4);
            AStar astar = new AStar(board);

            board.get(0, 1).setType(Cell.TYPE.BLOCKED);
            board.get(2, 1).setType(Cell.TYPE.BLOCKED);
            board.get(1, 2).setType(Cell.TYPE.BLOCKED);

            astar.setStart(0, 0);
            astar.setTarget(3, 3);

            List<Cell> path = astar.getPath();
            List<Cell> path2 = astar.getPath();
            List<Cell> path3 = astar.getPath();

            AStar.printPath(path);*/
//            AStar.printPath(path2);
//            AStar.printPath(path3);
        }
    }


