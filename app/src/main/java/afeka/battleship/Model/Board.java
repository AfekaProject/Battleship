package afeka.battleship.Model;

import java.util.Random;

public class Board {
    public static final int BOARD_SIZE = 10;
    private static final int[] SHIPS_SIZES = {4, 3, 2, 4, 3, 2, 5, 3, 1};
    private final int INDEX_X = 0;
    private final int INDEX_Y = 1;
    private  final int INDEX_DIRECTION=2;
    private final int HORIZONTAL = 1;
    private Tile[][] boardMatrix;
    private int shipsAlive;
    private Ship[] arrShip;

    public Board(int diff) {
        boardMatrix = new Tile[BOARD_SIZE][BOARD_SIZE];
        setShipsAlive(diff * 3);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++)
                boardMatrix[i][j] = new Tile(i, j);
        }
        arrShip = new Ship[getShipsAlive()];
        generateShips(getShipsAlive());
    }


    public int getShipsAlive() {
        return shipsAlive;
    }

    public void setShipsAlive(int shipsAlive) {
        this.shipsAlive = shipsAlive;
    }

    public boolean isWin() {
        shipsAlive--;
        return (shipsAlive == 0);
    }

    public Tile[][] getBoardMatrix() {
        return boardMatrix;
    }

    public Tile getTile(int i) {
        int row, col;
        row = i % BOARD_SIZE;
        col = i / BOARD_SIZE;
        return boardMatrix[row][col];
    }

    public int getSize() {
        return boardMatrix.length * boardMatrix[0].length;
    }

    private void generateShips(int numOfShips) {
        int[] vector;
        for (int i = 0; i < numOfShips; i++) {
            arrShip [i] = new Ship(SHIPS_SIZES[i]);
            arrShip[i].setId(i);
            vector = findFreePlace(arrShip[i]);

            for (int k = 0; k < arrShip[i].getSize(); k++) {

                boardMatrix[vector[INDEX_X]][vector[INDEX_Y]].setPlaced(arrShip[i]);
                arrShip[i].addTile(boardMatrix[vector[INDEX_X]][vector[INDEX_Y]]);
                if (vector[INDEX_DIRECTION] == HORIZONTAL)
                    vector[INDEX_X]++;
                else
                    vector[INDEX_Y]++;
            }
        }
    }
    public void shuffleShips (){
        int[] vector;
        Tile.Status status;
        for (int i = 0 ; i < arrShip.length ; i++ ){
            if (!arrShip[i].isDrowned(this)){
                vector = findFreePlace(arrShip[i]);
                for (int j = 0 ; j<arrShip[i].getSize();j++){
                    status = arrShip[i].getTile(j,this).getStatus();
                    boardMatrix[vector[INDEX_X]][vector[INDEX_Y]].setStatus(status);
                    boardMatrix[vector[INDEX_X]][vector[INDEX_Y]].setShip(arrShip[i]);
                    arrShip[i].getTile(j,this).setShip(null);
                    arrShip[i].getTile(j,this).setStatus(Tile.Status.NONE);
                    arrShip[i].updateIndex(j ,boardMatrix[vector[INDEX_X]][vector[INDEX_Y]]);
                    if (vector[INDEX_DIRECTION] == HORIZONTAL)
                        vector[INDEX_X]++;
                    else
                        vector[INDEX_Y]++;
                }
            }
        }
    }

    private int[] findFreePlace (Ship ship){
        int[] vector = new int[3];
        int direction, x, y, firstX, firstY;
        Random r = new Random();
        boolean okToPlace, readyToPlaced;
        direction = r.nextInt(2); // 1 = horizontal , 2 = vertical
        do {
            readyToPlaced = false;
            firstX = x = r.nextInt(BOARD_SIZE);
            firstY = y = r.nextInt(BOARD_SIZE);
            okToPlace = true;
            for (int k = 0; k < ship.getSize() && okToPlace; k++) {
                if (x < BOARD_SIZE && y < BOARD_SIZE) {
                    if (!boardMatrix[x][y].getStatus().equals(Tile.Status.NONE))      //tile already taken
                        okToPlace = false;
                    else {                                  // tile was free
                        if (direction == HORIZONTAL)
                            x++;
                        else
                            y++;
                    }
                } else
                    okToPlace = false;
            }
            if (okToPlace)
                readyToPlaced = true;
        } while (!readyToPlaced);
        // the ship can be placed
        vector[INDEX_X] = firstX;
        vector[INDEX_Y] = firstY;
        vector[INDEX_DIRECTION] = direction;
        return vector;
    }

}
