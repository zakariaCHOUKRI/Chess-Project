package src.controller;

import javafx.application.Platform;
import src.chess.Board.ActiveBoard;
import src.chess.Board.Board;
import src.chess.Board.Status;
import src.chess.Player.Player;
import src.chess.move.Move;
import src.chess.piece.Piece;

import javafx.scene.Scene;
import javafx.stage.Stage;
import src.square.Square;
import src.view.board.ActiveBoardView;
import src.view.board.BoardView;
import src.view.board.PlayView;
import src.view.board.Spot;
import src.view.piece.PieceView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class BoardController {
    public static final String TITLE = "Chess Game LBD III";
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;
    //bach nbkaw 3a9lin gha ela lpast move
    private static Stack<Move> pastMoves;

    private ActiveBoard activeBoard;
    private ActiveBoardView activeBoardView;

    private Status status;
    private boolean isWhiteTurn;

    private Player black;
    private Player white;

    public boolean isDown = false;

    /**
     * Constructor  kaycreer board controller bles informations d lboard li kaynin whad lfile
     * @param filePath lfile li ghayakhd mno les informations
     * @param white white player
     * @param black black player
     */
    public BoardController(String filePath, Player white, Player black) {
        setUpBoardFromFile(new File(filePath));
        pastMoves = new Stack<>();
        this.black = black;
        this.white = white;
        this.black.setBoardController(this);
        this.white.setBoardController(this);
        this.isWhiteTurn = false;
    }

    /**
     * taydir lboard ela hsab les informations li kaynin f lfile
     * @param file
     */
    public void setUpBoardFromFile(File file){
        try {
            //tayscanner lfile taykon fih f lwl lnombre d les pieces boyd ou
            // moraha ou les pieces bhad chkl (pawn par exemple):  P: {2, A, true, false}
            Scanner in = new Scanner(file);

            int numberOfWhitePieces = Integer.parseInt(in.nextLine().replaceAll("\\D", ""));
            String[] whitePieceString = new String[numberOfWhitePieces];
            Piece[] whitePieces = new Piece[numberOfWhitePieces];
            PieceView[] whitePieceViews = new PieceView[numberOfWhitePieces];
            for (int i = 0; i < numberOfWhitePieces; i++) {
                int finalI= i;
                whitePieceString[i] = in.nextLine();
                whitePieces[i] = Piece.parsePiece(whitePieceString[i]);
                whitePieceViews[i]=whitePieces[i].getPieceView();
                whitePieceViews[i].setOnMouseClicked(e-> this.clickedSquare(whitePieces[finalI].getSquare()));
            }

            int numberOfBlackPieces = Integer.parseInt(in.nextLine().replaceAll("\\D", ""));
            String[] blackPieceString = new String[numberOfWhitePieces];
            Piece[] blackPieces = new Piece[numberOfBlackPieces];
            PieceView[] blackPieceViews = new PieceView[numberOfBlackPieces];
            for (int i = 0; i < numberOfBlackPieces; i++) {
                int finalI = i;
                blackPieceString[i] = in.nextLine();
                blackPieces[i] = Piece.parsePiece(blackPieceString[i]);
                blackPieceViews[i] = blackPieces[i].getPieceView();
                blackPieceViews[i].setOnMouseClicked(e-> this.clickedSquare(blackPieces[finalI].getSquare()));
            }

            activeBoard = new Board(whitePieces,blackPieces,this);
            activeBoardView = new BoardView(whitePieceViews, blackPieceViews,createSpot());

        } catch (FileNotFoundException e) {
            //fihalat lfile makaynch
            System.err.print("CANNOT READ BOARD FROM FILE:");
            System.err.println(file.getAbsolutePath());
        }

    }
    // bach taycreer spot lkola sqaure kayn flboard
    private Spot[][] createSpot(){
        Spot[][] grid = new Spot[ROWS][COLUMNS];
        for (int r = 0; r < ROWS; r++) {
            int finalR = r;
            for (int c = 0; c < COLUMNS; c++) {
                int finalC = c;
                Spot spot = new Spot(r, c);
                spot.setOnMouseClicked(e-> this.clickedSquare(new Square(finalR,finalC)));
                grid[r][c] = spot;
            }
        }
        return grid;
    }


    public ActiveBoardView getActiveBoardView() {
        return activeBoardView;
    }
    public ActiveBoard getActiveBoard(){return activeBoard;}

    /**
     * fonction bach ndaw displaying lgame
     */
    public void startDisplay() {

        System.out.println("Setting up GUI");

        Scene scene = new Scene(activeBoardView.getBoardGUI(this));
        Stage window = PlayView.getPlayView(TITLE, scene);
        //fach  nbghiw nclosiz lgame ghayprinta window closed
        window.setOnCloseRequest(e -> {
            System.out.println("WINDOW CLOSED!!");
            isDown = true;
            white.stop();
            black.stop();
        });
        window.show();
        changeTurn();
    }

    /**
     * katexucter next move
     * @param move lmove li yalah texecuta
     * mora lexecytion ghaybdl dor b changeTurn()
     */
    public void executeNextMove(Move move) {
        if(isDown) {
            return;
        }
        //tanzido lmove l pastmoves moraha tandiroh ou tayban f lboard ou tatprinta lmove
        //moraha tancleariw highlights
        pastMoves.push(move);
        move.doMove(true);
        System.out.println(move.toString());
        activeBoardView.clearHighlights();
        //ila kan y9dr ykoun king en danger fend lenemy kanhighlightiw lking dlenemy (showWarning)
        if(activeBoard.checkForCheck(!isWhiteTurn)) {
            activeBoardView.showWarning(activeBoard.getKing(!isWhiteTurn).getSquare());
            //ila checkMate
            if(activeBoard.checkForCheckMate(!isWhiteTurn)) {
                System.out.printf("%s has won the game! and the game will exit in 5 seconds :D%n", isWhiteTurn ? "White" : "Black");
                status = Status.FREEZE;
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                Platform.exit();
                                System.exit(0);
                            }
                        },
                        5000
                );

            }
        } else {
            if(activeBoard.checkForStaleMate(!isWhiteTurn)) {
                System.out.printf("Stalemate and the game will exit in 5 seconds :D%n");
                status = Status.FREEZE;
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                Platform.exit();
                                System.exit(0);
                            }
                        },
                        5000
                );

            }
        }

        changeTurn();
    }
    //katchanger turn ila kan lkhl howa li m3b lbyd howa li
    // khasso yl3b ouL3kss sahih

    public void changeTurn() {
        isWhiteTurn = !isWhiteTurn;
        if(isWhiteTurn) {
            white.calculateNextMove();
        } else {
            black.calculateNextMove();
        }
    }
    //bach tan highlightiw lhints
    public void clickedSquare(Square square) {
        if(isWhiteTurn) {
            white.forwardBoardInput(square);
        } else {
            black.forwardBoardInput(square);
        }
    }

   // hado les fonctions  mkhssin lpastMoves (stack) ila bghina last move wla
   // nzido move wla nhydoh
    public static Move getLastMove(){
        if (!pastMoves.empty()) {
            return pastMoves.peek();
        }
        return null;
    }

    public static void pushMove(Move move){
        pastMoves.push(move);
    }
    public static void undoMove(boolean isVisual) {
        pastMoves.pop().undoMove(isVisual);
    }

}
