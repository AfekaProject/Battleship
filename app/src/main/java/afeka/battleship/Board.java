package afeka.battleship;

public class Board {

    enum tileStatus {Hit, Miss, None , Placed}
    private Tile[] boardMatrix;

    public Board (int size){
        boardMatrix = new Tile[size];
        for (int i = 0 ; i <size ; i++){
            boardMatrix[i] = new Tile();
        }
    }


    public void setTileInPosition(int i ,tileStatus status) {
        this.boardMatrix[i].setStatus(status);
    }

    public Tile getTileInPosition (int i){
        return boardMatrix[i];
    }

    public int getSize(){
        return boardMatrix.length;
    }
}
