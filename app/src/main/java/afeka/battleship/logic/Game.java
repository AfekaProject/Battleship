package afeka.battleship.logic;


import afeka.battleship.Model.Board;
import afeka.battleship.Model.Tile;

public class Game {

    public enum gameStatus {HIT, MISS, WRONG_MOVE, WIN}

    public enum turn {PLAYER, COMPUTER}

    public static final int BOARD_SIZE = 10;
    private ComputerPlayer cpu;
    private int difficult;
    private Board boardPlayer;
    private Board boardComputer;
    private gameStatus lastTurnStatus;
        private turn whosTurn;


    public Game(int diff) {
        this.difficult = diff;
        boardPlayer = new Board(BOARD_SIZE);
        boardComputer = new Board(BOARD_SIZE);
        whosTurn = turn.PLAYER;
        // playerLogicBoard = new LogicBoard(diff);
        //  cpuLogicBoard = new LogicBoard (diff);

    }
    public turn getWhosTurn() {
        return whosTurn;
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
    private void toggleTurn(){
         if(whosTurn == turn.PLAYER)
             whosTurn = turn.COMPUTER;
         else
             whosTurn = turn.PLAYER;
    }

    public gameStatus playGame(int position) {
        Tile currentTile;

        if(whosTurn == turn.PLAYER)
            currentTile = boardPlayer.getTile(position);
        else
            currentTile = boardComputer.getTile(position);


                if (currentTile.getStatus().equals(Tile.status.NONE)) { //current tile has nothing
                    currentTile.setMiss(); //change current tile - miss
                    lastTurnStatus = gameStatus.MISS;
                    toggleTurn();
                }
                else if(currentTile.getStatus().equals(Tile.status.PLACED)){ //current tile has ship
                    currentTile.setHit();
                    lastTurnStatus = gameStatus.HIT;
                }
                else if(currentTile.getStatus().equals(Tile.status.HIT)){  //current tile is hit
                    lastTurnStatus = gameStatus.WRONG_MOVE;

                }else{ //current tile is miss
                    lastTurnStatus = gameStatus.WRONG_MOVE;
                }




            return lastTurnStatus;

    }

    public void computerPlay(){
        playGame(cpu.playTurn(boardComputer));
    }
    public void playerPlay(int position){
        playGame(position);
    }


}
