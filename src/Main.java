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
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import static javafx.application.Application.launch;


public class Main extends Application{

        /*
        ====== SETTING THE BOARD ==============
         */
        //The board have 10 columns and 10 rows
        Board board = new Board(5,5);
        AStar astar = new AStar(board);
        //set start and end
        Cell start = new Cell(0,0);
        Cell end = new Cell(3,3);


         /*
           ====== CONSTANTS ==============
            */

        BooleanProperty showPathProperty = new SimpleBooleanProperty( true);

        MousePaintGestures mousePaint = new MousePaintGestures();
        MouseDragGestures mouseDrag;

        private boolean autoPath = true;

        Label status;


        @Override
        public void start(Stage stage){
            try {

                BorderPane root = new BorderPane();

                /*
                ============== TOOLBAR ===============================
                 */

                //Toolbar for the button and checkboxes

                HBox toolbar = new HBox();
                toolbar.setAlignment(Pos.CENTER);
                toolbar.setPadding(new Insets(10,10,10,10));
                toolbar.setSpacing(5);

                // remove block from the board
                Button removeBlocksButton = new Button("Remove Blocks");
                removeBlocksButton.setOnAction(event -> {
                    //set all the cells in board to type normal
                    board.setType(Cell.TYPE.NORMAL);

                    board.getCell(start.getX(),start.getY()).setType(Cell.TYPE.START);
                    board.getCell(end.getX(),end.getY()).setType(Cell.TYPE.END);
                     if(autoPath){
                         showWay();
                     }
                });

                //find Path Button
                Button findPathButton = new Button("Find Way");
                findPathButton.setOnAction(event -> {
                    showWay();
                });

                //show-hide path checkbox
                CheckBox showPathCheckBox = new CheckBox("Show Way");
                showPathCheckBox.selectedProperty().bindBidirectional(
                showPathProperty);
                showPathProperty.addListener((ChangeListener<Boolean>) ((observable, oldValue, newValue) -> {
                            repaintWay();
                    }
                    ));

                //Allow diagonals
                //TODO
                //

                toolbar.setAlignment(Pos.CENTER);

                // Set the element in the toolbar
                toolbar.getChildren().addAll(removeBlocksButton,findPathButton,showPathCheckBox);

                root.setTop(toolbar);

                /*
                ================= STATUS BAR =========================
                 */
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

                    /*
                =========== CREATE BOARD ====================
                 */

                // fill the board with cells
                for(int row = 0; row < board.getRows(); row++){
                    for(int col = 0; col < board.getCols(); col++){
                        Cell cell = new Cell(col,row,Cell.TYPE.NORMAL);
                        //board.set(col,row,cell);
                        board.getCell(col,row).setType(Cell.TYPE.NORMAL);
                        makePaintable(col,row);
                    }
                }


                // set the start/ end cell
                board.getCell(start.getX(),start.getY()).setType(Cell.TYPE.START);
                board.getCell(end.getX(),end.getY()).setType(Cell.TYPE.END);

                board.getStyleClass().add("board");
                //root.setCenter(board);
                root.setCenter(board);


                /*
                EXAMPLE ==============================================
                 */


//
//                start.toFront();
//                end.toFront();

                mouseDrag = new MouseDragGestures(board);
                mouseDrag.makeDragable(start);
                mouseDrag.makeDragable(end);


                createBlock();
                showWay();


                // redraw the path after release the mouse
//                board.addEventFilter(MouseEvent.MOUSE_RELEASED,onMouseReleased);
//                start.addEventFilter(MouseEvent.MOUSE_RELEASED,onMouseReleased);
//                end.addEventFilter(MouseEvent.MOUSE_RELEASED,onMouseReleased);

                /*
                =========== GENERATE SCENE =======================
                 */

                Scene scene = new Scene(root,board.getWidth(),board.getHeight());

                scene.getStylesheets().add(getClass().getResource("style/application.css").toExternalForm());

                stage.setScene(scene);
                stage.setTitle("A*Algo Implement");

                stage.show();



            }catch (Exception e){
                e.printStackTrace();
            }
        }



    /**
     * Fill the grid with arbitrary obstacles
     */
    private void createBlock() {

        board.get(0, 1).setType(Cell.TYPE.BLOCKED);
        board.get(1, 1).setType(Cell.TYPE.BLOCKED);
        board.get(2, 1).setType(Cell.TYPE.BLOCKED);
        board.get(3, 1).setType(Cell.TYPE.BLOCKED);

/*
        for (int row = 0; row < board.rows; row++) {
                for (int column = 0; column < board.cols; column++) {

                    if (row == 1 && column > 6)
                        board.getCell(column, row).setType(Cell.TYPE.BLOCKED);

                    if (row == 6 && column > 5)
                        board.getCell(column, row).setType(Cell.TYPE.BLOCKED);

                    if (row > 1 && row < 6 && column == 7)
                        board.getCell(column, row).setType(Cell.TYPE.BLOCKED);

                    if (row >= 0 && row < 7 && column == 2)
                        board.getCell(column, row).setType(Cell.TYPE.BLOCKED);

                    if (row >= 4 && row <= 7 && column == 5)
                        board.getCell(column, row).setType(Cell.TYPE.BLOCKED);

                }
        }
*/
    }
//        /*
//        Set up default to show the path
//         */
//        EventHandler<MouseEvent> onMouseReleased = event -> {
//          if(autoPath){
//              showWay();
//          }
//        };

        /*
        Search for the path and show if requested
         */
        public void showWay(){
            findWay(board.getCell(start.getX(),start.getY()),board.getCell(end.getX(),end.getY()));

        }

        public void repaintWay(){
            paintWay(astar.getPath());
        }

    /**
     * Remove Paint
     */
        public void removePaint() {
            for (int row = 0; row < board.getHeight(); row++) {
                for (int col = 0; col < board.getWidth(); col++) {
                    board.getCell(row,col).setType(Cell.TYPE.NORMAL);
                }
            }
        }
        /*

         */
        private void findWay(Cell start, Cell target){
            // generate a Path through the A* Algo
            astar.setStart(start.getX(),start.getY());
            astar.setTarget(target.getX(),target.getY());
            List<Cell> path = astar.getPath();

            String context;
            if(path!= null){
                context = "Cells in Path: "+ path.size();
            }else{
                context = "No Path to be found";
            }
            status.setText(context);

            //paint the path

            paintWay(path);

            astar.printPath(path);
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
                   // cell.getStyleClass().clear();
                    cell.setType(Cell.TYPE.PATH);
                }
            }else{
                System.out.println("NO WAY IS TO BE FOUND !");
            }
            board.getCell(start.getX(),start.getX()).setType(Cell.TYPE.START);
            board.getCell(end.getX(),end.getY()).setType(Cell.TYPE.END);
        }


    private void setType(MouseEvent event){
        Cell.TYPE type = null;

        if(event.isPrimaryButtonDown()){
            type = Cell.TYPE.BLOCKED;
        }else if(event.isSecondaryButtonDown()){
            type = Cell.TYPE.NORMAL;
        }else{
            //skip if nothing happens
            return;
        }
        Cell cell = (Cell) event.getSource();
        cell.setType(type);

        //remove highlight?? write later if necessary

    }
    /*
    ========== MOUSE PAINT GESTURE =====================================
     */
    // when click on the cell
    EventHandler<MouseEvent> onMousePressedEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            setType(event);
        }
    };

    EventHandler<MouseEvent> onDragDetected = event -> {
        // start press-grag-release gesture
        Cell cell = (Cell) event.getSource();
        cell.startFullDrag();
    };

    EventHandler<MouseEvent> onMouseEntered = event -> {
        //keep the paint when drag the mouse
        setType(event);
    };

    public void makePaintable(int col, int row){
        board.getCell(col,row).setOnMousePressed(onMousePressedEvent);
        board.getCell(col,row).setOnDragDetected(onDragDetected);
        board.getCell(col,row).setOnDragDetected(onMouseEntered);
    }
    /*
    ========== MAIN =====================================
     */


        public static void main(String[] args) {
            launch(args);

        }
    }


