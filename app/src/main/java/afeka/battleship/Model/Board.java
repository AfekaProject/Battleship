package afeka.battleship.Model;

public class Board {
    public final int SIZE = 10;

    public enum TileStatus {HIT, MISS, NONE, PLACED}
    private Tile[][] boardMatrix;


    public Board (int diff){

        boardMatrix = new Tile[SIZE][SIZE];
        for (int i = 0 ; i <SIZE ; i++){
            for(int j=0 ; j <SIZE ; j++)
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
        return (int) Math.sqrt(boardMatrix.length);
    }

    private void generateShips (){

    }
}
