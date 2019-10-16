//package input;
//
//
//import sun.javafx.util.Utils;
//import javafx.event.EventHandler;
//import javafx.scene.Node;
//import javafx.scene.input.MouseEvent;
//import com.Cell;
//import com.Board;
//
//public class MouseDragGestures {
//
//    class DragContext{
//        double x;
//        double y;
//    }
//
//    DragContext dragContext = new DragContext();
//
//    Board board;
//
//    double minWidth;
//    double maxWidth;
//
//    double minHeight;
//    double maxHeight;
//
//    public MouseDragGestures(Board board){ this.board = board;}
//
//    public void makeDragable(Node node){
//
//        node.addEventFilter(MouseEvent.MOUSE_PRESSED,onMousePressed );
//        node.addEventFilter(MouseEvent.MOUSE_DRAGGED, onMouseDrag);
//        node.addEventFilter(MouseEvent.MOUSE_RELEASED,onMouseReleased);
//
//    }
//
//    EventHandler<MouseEvent> onMousePressed = new EventHandler<MouseEvent>() {
//        @Override
//        public void handle(MouseEvent event) {
//            Cell cell = ((Cell) (event.getSource()));
//
//            dragContext.x = cell.getLayoutX() - event.getSceneX();
//            dragContext.y = cell.getLayoutY() - event.getSceneY();
//
//            minWidth = board.getMinWidth();
//            maxWidth = board.getMaxWidth();
//
//            minHeight = board.getMinHeight();
//            maxHeight = board.getMaxHeight();
//
//        }
//    };
//
//    EventHandler<MouseEvent> onMouseDrag = new EventHandler<MouseEvent>() {
//        @Override
//        public void handle(MouseEvent event) {
//            Cell cell = ((Cell) (event.getSource()));
//
//            /**
//             * Simple utility function which clamps the given value to be strictly
//             * between the min and max values.
//             */
//            double x = Utils.clamp(dragContext.x  + event.getSceneX(),minWidth,maxWidth);
//
//            double y = Utils.clamp(dragContext.y  + event.getSceneY(),minHeight,maxHeight);
//
//            cell.setLayoutX(x);
//            cell.setLayoutY(y);
//        }
//    };
//
//
//    EventHandler<MouseEvent> onMouseReleased = new EventHandler<MouseEvent>() {
//        @Override
//        public void handle(MouseEvent event) {
//            Cell cell = ((Cell) (event.getSource()));
//
//            //snap the cell to board
//            board.snapToGrid(cell);
//        }
//    };
//
//}
//
