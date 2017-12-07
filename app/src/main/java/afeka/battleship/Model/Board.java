package afeka.battleship.Model;

import java.util.Random;

public class Board {
    public static final int BOARD_SIZE = 10;

    private Tile[][] boardMatrix;

    public Board (){

        boardMatrix = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++)
            boardMatrix[i][j]= new Tile(i,j);
        }

    }

    public Tile[][] getBoardMatrix() {
        return boardMatrix;
    }

    /* public void setTile(int i , TileStatus Status) {
        this.boardMatrix[i].setStatus(Status);
    }
*/

    
    public Tile getTile(int i){
        int row,col;
        row = i % BOARD_SIZE;
        col = i / BOARD_SIZE;

        return boardMatrix[row][col];
    }

    public int getSize(){
        return (int) Math.sqrt(boardMatrix.length);
    }


}
