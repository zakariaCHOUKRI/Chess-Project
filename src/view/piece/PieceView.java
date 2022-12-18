package src.view.piece;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import src.chess.piece.Piece;
import src.square.Square;
import src.view.board.ActiveBoardView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PieceView extends ImageView{
    public static final long TRANSITION_TIME = 400; // speed of the animation

    // this method animates the image of the piece from its current square to its next one
    public void updateSquare(Square square){
        TranslateTransition tf = new TranslateTransition(Duration.millis(TRANSITION_TIME),this);
        tf.setToX(square.getCol() * ActiveBoardView.SPOT_WIDTH);
        tf.setToY(square.getRow() * ActiveBoardView.SPOT_WIDTH);
        tf.play();
    }

    // this method makes the piece invisible after it is killed
    public void kill() {
        //TODO this method hasn't been test
        setVisible(false);
    }

    // this method makes the piece visible after it is revived
    public void revive() {
        //TODO this method hasn't been test
        setVisible(true);
    }

    // this method fetches the image for the piece and puts it on the board
    public void setupIcon(String filePath, Piece piece) {
        filePath += piece.isWhite() ? "/White_" : "/Black_";
        filePath += piece.getName() + ".png";

        try {
            Image image = new Image(new FileInputStream(filePath));
            setImage(image);
            setFitHeight(ActiveBoardView.SPOT_WIDTH);
            setFitWidth(ActiveBoardView.SPOT_WIDTH);
            setPreserveRatio(true);

            setTranslateX(piece.getSquare().getCol() * ActiveBoardView.SPOT_WIDTH);
            setTranslateY(piece.getSquare().getRow() * ActiveBoardView.SPOT_WIDTH);
        } catch(FileNotFoundException e) {
            System.err.printf("NO SUCH FILE \"%s\"%n", filePath);
        }
    }

}
