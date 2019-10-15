package input;

import com.Board;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import com.Cell;


public class MousePaintGestures {
    private Board board;
    EventHandler<MouseEvent> onMousePressedEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
        setType(event);
        }
    };

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

    EventHandler<MouseEvent> onDragDetected = event -> {
        // start press-grag-release gesture
        Cell cell = (Cell) event.getSource();
        cell.startFullDrag();
    };

    EventHandler<MouseEvent> onMouseEntered = event -> {
        //keep the paint when drag the mouse
        setType(event);
    };

    public void makePaintable(int col, int row ){
        board.getCell(col, row).setOnMousePressed(onMousePressedEvent);
        board.getCell(col, row).setOnDragDetected(onDragDetected);
        board.getCell(col, row).setOnDragDetected(onMouseEntered);
    }


}
