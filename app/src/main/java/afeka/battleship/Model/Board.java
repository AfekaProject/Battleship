package afeka.battleship.Model;

public class Board {

    public enum TileStatus {HIT, MISS, NONE, PLACED}
    private Tile[] boardMatrix;

    public Board (int size){
        boardMatrix = new Tile[size];
        for (int i = 0 ; i <size ; i++){
            boardMatrix[i] = new Tile();
        }
    }


    public void setTile(int i , TileStatus status) {
        this.boardMatrix[i].setStatus(status);
    }


    
    public Tile getTile(int i){
        return boardMatrix[i];
    }

    public int getSize(){
        return boardMatrix.length;
    }
}
