package src.view.board;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayView {
     public static Stage getPlayView(String TITLE,Scene scene){
         Stage window = new Stage();
         window.setTitle(TITLE);
         //sets title to the window
         window.setResizable(false);
         // so that the window is set to a specific constant size
         window.setScene(scene);
         return window;
     }
}
