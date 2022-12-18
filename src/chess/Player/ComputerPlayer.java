package src.chess.Player;

import src.chess.AI.MinimaxAI;
import src.chess.AI.AISettings;
import src.controller.BoardController;
import src.square.Square;

public class ComputerPlayer extends Player {
    //AI
    private MinimaxAI ai;

    /**
     * Constructor dComputerPlayer
     * @param isWhite
     */
    public ComputerPlayer(boolean isWhite) {
        super(isWhite);
        //kaykhss lAI
        this.ai = new MinimaxAI(isWhite, AISettings.chooseAISettings());
    }


    @Override
    //kaykhss lAI bhala tay3lm lAI ina board hna khdamin fih
    public void setBoardController(BoardController boardController) {
        super.setBoardController(boardController);
        ai.setActiveBoard(boardController.getActiveBoard());
    }
    //kaykhss LAI
    public void calculateNextMove() {
        System.out.println("Starting AI Thread");
        new Thread(() -> boardController.executeNextMove(ai.makeMove())).start();
    }
    //kayktb bli AI is thinking f lconsole
    public void forwardBoardInput(Square square) {
        System.out.println(ai.getName() + " is thinking");
    }
    //bach yhbss LAI
    public void stop() {
        ai.stop();
    }
    /**
     * toString
     * @return type dlplayer
     */
    public String toString() {
        return "Computer";
    }
}
