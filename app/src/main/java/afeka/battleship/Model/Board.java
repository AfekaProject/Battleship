package afeka.battleship.Model;

public class Board {

    public enum TileStatus {HIT, MISS, NONE, PLACED}
    private Tile[][] boardMatrix;
    private int size;

    public Board (int size){
        this.size = size;
        boardMatrix = new Tile[size][size];
        for (int i = 0 ; i <size ; i++){
            for(int j=0 ; j <size ; j++)
            boardMatrix[i][j]= new Tile(i,j);
        }
    }


   /* public void setTile(int i , TileStatus status) {
        this.boardMatrix[i].setStatus(status);
    }
*/

    
    public Tile getTile(int i){
        int row,col;
        row = i % size;
        col = i / size;

        return boardMatrix[row][col];
    }

    public int getSize(){
        return (int) Math.sqrt((double)boardMatrix.length);
    }
}
