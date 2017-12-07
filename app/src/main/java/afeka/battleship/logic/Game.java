package afeka.battleship.logic;

import afeka.battleship.Model.Board;
import afeka.battleship.Model.Tile;

public class Game {

    public enum GameStatus {HIT, MISS, WRONG_MOVE, WIN}

    public enum Turn {PLAYER, COMPUTER}

    private ComputerPlayer cpu;
    private int difficult;
    private Board bPlayer;
    private Board bComputer;
    private GameStatus lastTurnStatus;
    private Turn whosTurn;


    public Game(int diff) {
        this.difficult = diff;
        cpu = new ComputerPlayer();
        bPlayer = new Board(difficult);
        bComputer = new Board(difficult);
        whosTurn = Turn.PLAYER;
    }
    public Turn getWhosTurn() {
        return whosTurn;
    }

    private void toggleTurn(){
         if(whosTurn == Turn.PLAYER)
             whosTurn = Turn.COMPUTER;
         else
             whosTurn = Turn.PLAYER;
    }

    public Board getBoard (Turn t){
        if (t.equals(Turn.PLAYER))
            return bPlayer;
        else
            return bComputer;
    }

    public GameStatus playGame(int position) {
        Tile currentTile;

        if(whosTurn == Turn.PLAYER)
            currentTile = bPlayer.getTile(position);
        else
            currentTile = bComputer.getTile(position);


                if (currentTile.getStatus().equals(Tile.Status.NONE)) { //current tile has nothing
                    currentTile.setMiss(); //change current tile - miss
                    lastTurnStatus = GameStatus.MISS;
                    toggleTurn();
                }
                else if(currentTile.getStatus().equals(Tile.Status.PLACED)){ //current tile has ship
                    currentTile.setHit();
                    lastTurnStatus = GameStatus.HIT;
                }
                else if(currentTile.getStatus().equals(Tile.Status.HIT)){  //current tile is hit
                    lastTurnStatus = GameStatus.WRONG_MOVE;

                }else{ //current tile is miss
                    lastTurnStatus = GameStatus.WRONG_MOVE;
                }

            return lastTurnStatus;

    }

    public void computerPlay(){
        playGame(cpu.playTurn(bComputer));
    }
    public void playerPlay(int position){
        playGame(position);
    }


}
