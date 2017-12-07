package afeka.battleship.logic;

import afeka.battleship.Model.Board;
import afeka.battleship.Model.Fleet;
import afeka.battleship.Model.Tile;

public class Game {

    public enum gameStatus {HIT, MISS, WRONG_MOVE, WIN}

    public enum turn {PLAYER, COMPUTER}

    public static final int BOARD_SIZE = 10;
    private ComputerPlayer cpu;
    private int difficult;
    private Fleet fPlayer;
    private Fleet fComputer;
    private gameStatus lastTurnStatus;
        private turn whosTurn;


    public Game(int diff) {
        this.difficult = diff;
        fPlayer = new Fleet(difficult);
        fComputer = new Fleet(difficult);
        whosTurn = turn.PLAYER;
    }
    public turn getWhosTurn() {
        return whosTurn;
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
            currentTile = fPlayer.getBoard().getTile(position);
        else
            currentTile = fComputer.getBoard().getTile(position);


                if (currentTile.getStatus().equals(Tile.Status.NONE)) { //current tile has nothing
                    currentTile.setMiss(); //change current tile - miss
                    lastTurnStatus = gameStatus.MISS;
                    toggleTurn();
                }
                else if(currentTile.getStatus().equals(Tile.Status.PLACED)){ //current tile has ship
                    currentTile.setHit();
                    lastTurnStatus = gameStatus.HIT;
                }
                else if(currentTile.getStatus().equals(Tile.Status.HIT)){  //current tile is hit
                    lastTurnStatus = gameStatus.WRONG_MOVE;

                }else{ //current tile is miss
                    lastTurnStatus = gameStatus.WRONG_MOVE;
                }




            return lastTurnStatus;

    }

    public void computerPlay(){
        playGame(cpu.playTurn(fComputer.getBoard()));
    }
    public void playerPlay(int position){
        playGame(position);
    }


}
