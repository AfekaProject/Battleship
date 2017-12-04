package afeka.battleship;



public class Board {



    private Tile [] boardMatrix;

    public Board (int size){
        boardMatrix = new Tile[size];
        for (int i = 0 ; i <size ; i++){
            boardMatrix[i] = new Tile(Tile.status.None);
        }
    }


    public void setTileInPosition(int i ,Tile.status status) {
        this.boardMatrix[i].setStatus(status);
    }

    public Tile getTileInPosition (int i){
        return boardMatrix[i];
    }


}
