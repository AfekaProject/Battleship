package afeka.battleship.logic;

import afeka.battleship.Model.Board;
import afeka.battleship.Model.Tile;

public class Game {
    public static final boolean debug = true;      // enemy ships shown
    public static final boolean aiWin = false;      // make the ai win

    public static final String DIFFICULTY = "Difficulty";
    public static final String WHO_WIN = "WhoWin";
    public static final String END_BUNDLE = "WinAndDif";
    public static final String LOCATION_BUNDLE = "Location";
    public static final String LAT = "Latitude";
    public static final String LONG = "Longitude";
    public static final String SCORE = "currentScore";
    public static final String SCORE_BUNDLE = "Score";

    public enum GameStatus {HIT, MISS, WRONG_MOVE, DROWN, WIN}

    public enum Players {PLAYER, COMPUTER}

    private ComputerPlayer cpu;
    private Board bPlayer;
    private Board bComputer;

    private Players CurrentTurn;

    public Game(int difficult) {
        cpu = new ComputerPlayer();
        bPlayer = new Board(difficult);
        bComputer = new Board(difficult);
        CurrentTurn = Players.PLAYER;
    }

    public Players getCurrentTurn() {
        return CurrentTurn;
    }

    private void toggleTurn() {
        if (CurrentTurn == Players.PLAYER)
            CurrentTurn = Players.COMPUTER;
        else
            CurrentTurn = Players.PLAYER;
    }

    public Board getBoard(Players t) {
        if (t.equals(Players.PLAYER))
            return bPlayer;
        else
            return bComputer;
    }

    private GameStatus playGame(int position) {
        Board currentBoard;
        Tile currentTile;
        GameStatus lastTurnStatus;

        if (getCurrentTurn() == Players.PLAYER) {
            currentBoard = bPlayer;
            currentTile = bPlayer.getTile(position);
        } else {
            currentBoard = bComputer;
            currentTile = bComputer.getTile(position);
        }
        if (currentTile.getStatus().equals(Tile.Status.NONE)) { //current tile has nothing
            currentTile.setMiss(); //change current tile - miss
            lastTurnStatus = GameStatus.MISS;
            toggleTurn();
        } else if (currentTile.getStatus().equals(Tile.Status.PLACED)) { //current tile has ship
            lastTurnStatus = GameStatus.HIT;
            if (currentTile.setHit(currentBoard)) {// return if a ship got drowned
                lastTurnStatus = GameStatus.DROWN;
                if (currentBoard.isWin())
                    lastTurnStatus = GameStatus.WIN;
            }

        } else if (currentTile.getStatus().equals(Tile.Status.HIT))  //current tile is hit
            lastTurnStatus = GameStatus.WRONG_MOVE;
        else                      //current tile is miss
            lastTurnStatus = GameStatus.WRONG_MOVE;

        return lastTurnStatus;
    }

    public GameStatus computerPlay(Board board) {

        return playGame(cpu.playTurn(board));
    }

    public GameStatus playerPlay(int position) {
        return playGame(position);
    }

    public int getLastPosition(){
        return cpu.getLastPosition();
    }

    public void setCurrentTurn(Players currentTurn) {
        CurrentTurn = currentTurn;
    }

}
