package afeka.battleship.logic;


import afeka.battleship.Model.Board;
import afeka.battleship.Model.Fleet;
import afeka.battleship.logic.ComputerPlayer;

public class Game {

    public static final int BOARD_SIZE = 100;
    private ComputerPlayer cpu;
    private int difficult;
    private Board bPlayerToShow;
    private Board bComputerToShow;
    private Fleet playerShips;
    private Fleet computerShips;




    public Game (int diff){
        this.difficult = diff;
        bPlayerToShow = new Board(BOARD_SIZE);
        bComputerToShow = new Board( BOARD_SIZE);
        playerShips = new Fleet(diff);
        computerShips = new Fleet (diff);

    }

    public Board getbPlayerToShow() {
        return playerShips.getLogicBoard();
    }

    public Board getbComputerToShow() {
        return bComputerToShow;
    }
}
