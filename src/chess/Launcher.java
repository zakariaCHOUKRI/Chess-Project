package src.chess;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import src.controller.BoardController;
import src.chess.Player.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import src.view.board.Spot;

import java.io.File;

public class Launcher extends Application {
    public BoardController b;
    private Stage launcherWindow;
    public static File filePath = new File((new File("")).getAbsolutePath() + "/Resources");

    private Player blackPlayer;
    private Player whitePlayer;

    @Override
    public void start(Stage launcherWindow) {
        this.launcherWindow = launcherWindow;

        Label white = new Label("White Player");
        white.setTextFill(Color.web("#000000"));
        Label black = new Label("Black Player");
        black.setTextFill(Color.web("#000000"));

        HBox titles = new HBox(10);
        titles.getChildren().addAll(white, black);
        titles.setAlignment(Pos.CENTER);

        ChoiceBox<String> whitePlayers = new ChoiceBox<>();
        whitePlayers.getItems().addAll(Player.PLAYER_TYPES);
        whitePlayers.setOnAction(e -> whitePlayer = Player.parsePlayer(whitePlayers.getValue(), true));

        ChoiceBox<String> blackPlayers = new ChoiceBox<>();
        blackPlayers.getItems().addAll(Player.PLAYER_TYPES);
        blackPlayers.setOnAction(e -> blackPlayer = Player.parsePlayer(blackPlayers.getValue(), false));


        HBox playerSelectors = new HBox(10);
        playerSelectors.getChildren().addAll(whitePlayers, blackPlayers);
        playerSelectors.setAlignment(Pos.CENTER);

        Label theme = new Label("Theme");
        theme.setTextFill(Color.web("#000000"));
        HBox themeTitle = new HBox(10);
        themeTitle.getChildren().addAll(theme);
        themeTitle.setAlignment(Pos.CENTER);
        ChoiceBox<String> themeChoice = new ChoiceBox<>();
        String themes[] = {"Chess.com Theme", "Classic Theme", "Ice Theme", "Wooden Theme"};
        themeChoice.getItems().addAll(themes);
        themeChoice.setOnAction(e -> Spot.setColor(themeChoice.getValue()));
        HBox themeSelectors = new HBox(10);
        themeSelectors.getChildren().addAll(themeChoice);
        themeSelectors.setAlignment(Pos.CENTER);


        Button button = new Button("START THE GAME");
        button.setOnAction(e -> {
            b = new BoardController(filePath.getAbsolutePath() + "/Boards/DefaultBoard.txt", whitePlayer, blackPlayer);
            launcherWindow.close();
            b.startDisplay();
        });

        // add the icon of a black pawn to the window
        launcherWindow.getIcons().add(new Image(Launcher.filePath.getAbsolutePath() + "\\ChessPieceImages" + "\\Black_Pawn.png"));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(titles, playerSelectors, themeTitle, themeSelectors, button);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        // do not use bg1, bg6, bg8, 10 is the best candidate
        BackgroundImage myBI= new BackgroundImage(new Image(Launcher.filePath.getAbsolutePath() + "\\ChessPieceImages" + "\\bg10.jpg", 564, 564,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        layout.setBackground(new Background(myBI));

        Scene scene = new Scene(layout, 564, 564);

        launcherWindow.setScene(scene);
        launcherWindow.setResizable(false);
        launcherWindow.setTitle("Chess Game LBD III");
        launcherWindow.show();




    }

    public static void main(String[] args) {
        launch();
    }
}
