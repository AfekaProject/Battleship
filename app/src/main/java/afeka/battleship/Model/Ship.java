package afeka.battleship.Model;

public class Ship {
    private final int INDEX_X = 0;
    private final int INDEX_Y = 1;
    private int size;
    private int liveCounter;
    private int id;
    private int[][] tileIndex;


    public int getSize() {
        return size;
    }

    public Ship(int size) {
        this.size = size;
        liveCounter = 0;
        tileIndex = new int[size][2];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addTile(Tile t) {
        tileIndex[liveCounter][INDEX_X] = t.getX();
        tileIndex[liveCounter][INDEX_Y] = t.getY();
        liveCounter++;
    }

    public boolean isDrowned(Board board) {
        if (liveCounter == 0) {
            for (int i = 0; i < size; i++) {
                board.getBoardMatrix()[tileIndex[i][INDEX_X]][tileIndex[i][INDEX_Y]].setDrowned();
            }
            return true;
        } else
            return false;
    }

    public boolean setHit(Board board) {
        liveCounter--;
        return isDrowned(board);
    }

    public Tile getTile (int i , Board board){
        int[] index = new int [2];
        index[INDEX_X] = tileIndex[i][INDEX_X];
        index[INDEX_Y] = tileIndex[i][INDEX_Y];
        return board.getBoardMatrix()[index[INDEX_X]][index[INDEX_Y]];
    }

    public void updateIndex (int i , Tile t){
        tileIndex[i][INDEX_X] = t.getX();
        tileIndex[i][INDEX_Y] = t.getY();
    }


}