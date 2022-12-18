package src.view.board;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Spot extends StackPane {
    public final int HIGHLIGHT_WIDTH = 64;
    //value of highlight width
    public final Color HIGHLIGHT_COLOR = new Color(0.9607843137254902F, 0.9607843137254902F, 0.52734375F, 0.5F);
    //color of highlight
    public final Color WARNING_COLOR = Color.RED;
    //color of warning
    public static Color WHITE_COLOR1 = new Color(0.9296875, 0.9296875, 0.8203125, 1); // chess.com theme
    public static Color WHITE_COLOR2 = Color.WHITE; // classic theme
    public static Color WHITE_COLOR3 = new Color(0.8203125, 0.87109375, 0.89453125, 1); // ice theme
    public static Color WHITE_COLOR4 = new Color(0.9450980392156863, 0.84765625, 0.70703125, 1); // wooden theme
    //square color
    public static Color BLACK_COLOR1 = new Color(0.4609375, 0.5859375, 0.3359375, 1);
    public static Color BLACK_COLOR2 = Color.DARKGRAY;
    public static Color BLACK_COLOR3 = new Color(0.453125, 0.59375, 0.6796875, 1);
    public static Color BLACK_COLOR4 = new Color(0.7098039215686275, 0.5294117647058824, 0.38671875, 1);
    //square color

    public static Color WHITE_COLOR;
    public static Color BLACK_COLOR;

    public static void setColor(String theme) {
        switch (theme) {
            case ("Chess.com Theme"):
                WHITE_COLOR = WHITE_COLOR1;
                BLACK_COLOR = BLACK_COLOR1;
                break;

            case ("Classic Theme"):
                WHITE_COLOR = WHITE_COLOR2;
                BLACK_COLOR = BLACK_COLOR2;
                break;

            case ("Ice Theme"):
                WHITE_COLOR = WHITE_COLOR3;
                BLACK_COLOR = BLACK_COLOR3;
                break;

            case ("Wooden Theme"):
                WHITE_COLOR = WHITE_COLOR4;
                BLACK_COLOR = BLACK_COLOR4;
                break;
        }
    }

    private final Rectangle highlight;
    private final Rectangle warning;
    private final Rectangle highlightCover;

    //highlights, warnings, and highlight cover have the form of squares

    public Spot(int row, int column) {

        setTranslateX(column * ActiveBoardView.SPOT_WIDTH);
        setTranslateY(row * ActiveBoardView.SPOT_WIDTH);
        //translate to be positioned  at the specified spot


        boolean isWhite = (row + column) % 2 == 0;

        Rectangle square = new Rectangle(ActiveBoardView.SPOT_WIDTH, ActiveBoardView.SPOT_WIDTH);
        //square of width 64px
        highlight = new Rectangle(ActiveBoardView.SPOT_WIDTH, ActiveBoardView.SPOT_WIDTH);
        //highlight is a square of width 64px, same size as a board square
        warning = new Rectangle(ActiveBoardView.SPOT_WIDTH, ActiveBoardView.SPOT_WIDTH);
        //warning is a square of width 64px, same size as a board square
        highlightCover = new Rectangle(ActiveBoardView.SPOT_WIDTH - HIGHLIGHT_WIDTH, ActiveBoardView.SPOT_WIDTH - HIGHLIGHT_WIDTH);
        //highlight cover takes as width the difference between the highlight width and spot width so that
        // only the borders are highlighted

        if(isWhite) {
            square.setFill(WHITE_COLOR);
            highlightCover.setFill(WHITE_COLOR);
            //fills square with colors
        } else {
            square.setFill(BLACK_COLOR);
            highlightCover.setFill(BLACK_COLOR);
            //fills square with colors
        }
        highlight.setFill(HIGHLIGHT_COLOR);

        warning.setFill(WARNING_COLOR);
        // when initializing the sport,we put the highlights by default, but as you ll see further
        // in the code, the highlight is visible only we set the method isVisible to True

        clear();

        getChildren().add(square);
        getChildren().add(highlight);
        getChildren().add(warning);
        getChildren().add(highlightCover);
    }

    /**
     * allow highlight
     */
    protected void highLight() {
        //
        highlight.setVisible(true);
        highlightCover.setVisible(true);
        //show highlights
    }

    /**
     * allow warning
     */
    protected void warn() {
        warning.setVisible(true);
        highlightCover.setVisible(true);
        //show warnings
    }

    /**
     * make highlight, warning, highlightCover become invisible
     */
    protected void clear() {
        highlight.setVisible(false);
        warning.setVisible(false);
        highlightCover.setVisible(false);
        //sets square to its original form
    }

}
