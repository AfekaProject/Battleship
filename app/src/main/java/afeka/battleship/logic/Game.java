package afeka.battleship.logic;


import afeka.battleship.Model.Board;

public class Game {

    public static final int BOARD_SIZE = 10;
    private ComputerPlayer cpu;
    private int difficult;
    private Board bPlayerToShow;
    private Board bComputerToShow;
    private LogicBoard playerLogicBoard;
    private LogicBoard cpuLogicBoard;


    public Game (int diff){
        this.difficult = diff;
        bPlayerToShow = new Board(BOARD_SIZE);
        bComputerToShow = new Board(BOARD_SIZE);
        playerLogicBoard = new LogicBoard(diff);
        cpuLogicBoard = new LogicBoard (diff);

    }

    public Board getbPlayerToShow() {
        return playerShips.getLogicBoard();
    }

    public Board getbComputerToShow() {
        return bComputerToShow;
    }

    public void generateSheeps() {
        //need to be changed!!


    }

}
