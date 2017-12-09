package afeka.battleship.Model;

import java.util.Random;

public class Board {
    public static final int BOARD_SIZE = 10;

    private Tile[][] boardMatrix;
    private int shipsAlive;

    public Board (int diff){
        boardMatrix = new Tile[BOARD_SIZE][BOARD_SIZE];
      //  setShipsAlive(diff*3);
        setShipsAlive(1);
        for (int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++)
                boardMatrix[i][j]= new Tile(i,j);
        }
        //generateShips(1);
        generateShipCeck();
    }



    public int getShipsAlive() {
        return shipsAlive;
    }

    public void setShipsAlive(int shipsAlive) {
        this.shipsAlive = shipsAlive;
    }

    public boolean isWin(){
        shipsAlive--;
        return (shipsAlive==0);
    }

    public Tile[][] getBoardMatrix() {
        return boardMatrix;
    }

    public Tile getTile(int i){
        int row,col;
        row = i % BOARD_SIZE;
        col = i / BOARD_SIZE;
        return boardMatrix[row][col];
    }

    public int getSize(){
        return boardMatrix.length*boardMatrix[0].length;
    }

    private void generateShipCeck(){
        Ship ship = new Ship(1);
        ship.setId(0);
        boardMatrix[0][0].setPlaced(ship);
    }
    private void generateShips (int numOfShips) {

        int direction, x, y, firstX, firstY;
        Random r = new Random();
        boolean okToPlace, readyToPlaced;
        for (int i = 0; i < numOfShips; i++) {
            Ship ship = new Ship(r.nextInt(4)+1);
            ship.setId(i);
            direction = r.nextInt(2); // 1 = horizontal , 2 = vertical
            do {
                readyToPlaced = false;
                firstX = x = r.nextInt(BOARD_SIZE);
                firstY = y = r.nextInt(BOARD_SIZE);
                okToPlace = true;
                for (int k = 0; k < ship.getSize() && okToPlace; k++) {
                    if (x < BOARD_SIZE && y < BOARD_SIZE) {
                        if (boardMatrix[x][y].isPlaced())      //tile already taken
                            okToPlace = false;
                        else {                                  // tile was free
                            if (direction == 1)
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
            x = firstX;
            y = firstY;
            for (int k = 0; k < ship.getSize() && okToPlace; k++) {
                boardMatrix[x][y].setPlaced(ship);
                if (direction == 1)
                    x++;
                else
                    y++;
            }
        }
    }
}
