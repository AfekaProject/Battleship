package afeka.battleship.Model;

public class Board {


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
        return boardMatrix[positionToX(i)][positionToY(i)];
    }
    private int positionToX(int position){ //convert position to x
        return position % size;
    }
    private int positionToY(int position){ //convert position to y
        return position / size;
    }

    public int getSize(){
        return (int) Math.sqrt((double)boardMatrix.length);
    }
}
