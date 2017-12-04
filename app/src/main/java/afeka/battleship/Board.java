package afeka.battleship;

public class Board {

    private TileView[] boardMatrix;

    public Board (int size){
        boardMatrix = new TileView[size];
        for (int i = 0 ; i <size ; i++){
            boardMatrix[i] = new TileView(TileView.status.None);
        }
    }


    public void setTileInPosition(int i ,TileView.status status) {
        this.boardMatrix[i].setStatus(status);
    }

    public TileView getTileInPosition (int i){
        return boardMatrix[i];
    }

    public int getSize(){
        return boardMatrix.length;
    }
}
