package afeka.battleship.Model;

public class Board {
    public final int SIZE = 10;


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
        return boardMatrix[positionToX(i)][positionToY(i)];
    }
    private int positionToX(int position){ //convert position to x
        return position % size;
    }
    private int positionToY(int position){ //convert position to y
        return position / size;
    }

    public int getSize(){
        return (int) Math.sqrt(boardMatrix.length);
    }

    private void generateShips (){

    }
}
