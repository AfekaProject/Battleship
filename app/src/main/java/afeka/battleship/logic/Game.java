package afeka.battleship.logic;

import afeka.battleship.Model.Board;
import afeka.battleship.Model.Tile;

public class Game {

    public enum GameStatus {HIT, MISS, WRONG_MOVE, WIN}

    public enum Players {PLAYER, COMPUTER}

    private ComputerPlayer cpu;
    private int difficult;
    private Board bPlayer;
    private Board bComputer;
    private GameStatus lastTurnStatus;
    private Players whosTurn;


    public Game(int diff) {
        this.difficult = diff;
        cpu = new ComputerPlayer();
        bPlayer = new Board(difficult);
        bComputer = new Board(difficult);
        whosTurn = Players.PLAYER;
    }
    public Players getWhosTurn() {
        return whosTurn;
    }

    private void toggleTurn(){
         if(whosTurn == Players.PLAYER)
             whosTurn = Players.COMPUTER;
         else
             whosTurn = Players.PLAYER;
    }

    public Board getBoard (Players t){
        if (t.equals(Players.PLAYER))
            return bPlayer;
        else
            return bComputer;
    }

    public GameStatus playGame(int position) {
        Tile currentTile;

        if(whosTurn == Players.PLAYER)
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
                else if(currentTile.getStatus().equals(Tile.Status.HIT) || currentTile.getStatus().equals(Tile.Status.MISS)  ) {  //current tile is hit
                    lastTurnStatus = GameStatus.WRONG_MOVE;
                }

            return lastTurnStatus;

    }

    public GameStatus computerPlay(){
        return playGame(cpu.playTurn(bComputer));
    }
    public GameStatus playerPlay(int position){
        return playGame(position);
    }


}
