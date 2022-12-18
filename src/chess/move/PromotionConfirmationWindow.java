package src.chess.move;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.LinkedList;

/**
 * hada howa lwindow li kaykhli lplayer ikhtar ina piece bgha fach
 * ghaydir lpromotion dyal lpawn
 */
public class PromotionConfirmationWindow {
    private static Move finalMove;

    /**
     * katcreer wahd lwindow li fih lpiece li y9dr lplayer ykhtar
     *
     * @param title titre d lwindow li tayban
     * @param message lmessage likay displaya luser
     * @param moves LinkedList dlmoves
     * @return finalMove
     */
    public static Move display(String title, String message, LinkedList<Move> moves) {
        Stage window = new Stage();

        //  APPLICATION_MODAL : hiya li kadefini wahd lmodel window bach taybloki jami3 levents mn litayjiw mn bra (windows loukhrin)
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        //message li ghayban luser li fih  "Which piece you would like to replace the pawn with?"
        Label label = new Label();
        label.setText(message);
        label.setTextAlignment(TextAlignment.CENTER);
        //choices li end luser lh9 ydirhoum li homa queen knight rook bishop
        ChoiceBox<Move> moveChoiceBox = new ChoiceBox<>();
        moveChoiceBox.getItems().addAll(moves);
        // lbutton dyal done
        Button button = new Button("Done");
        button.setOnAction(e -> {
            finalMove = moveChoiceBox.getValue();
            window.close();
        });
        // Vbox layout hoa li ghadi yjm label ou choices ou lbutton f window ou 10 howa spacing li ghaykoun banithoum
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, moveChoiceBox, button);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return finalMove;
    }
}
