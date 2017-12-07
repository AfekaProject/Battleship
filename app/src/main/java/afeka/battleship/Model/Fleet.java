package afeka.battleship.Model;


import java.util.Random;

public class Fleet {
    private Board board;
   // private Ship [] ships;
    private int shipsAlive;

    public Fleet (int diff){
        board = new Board();
        shipsAlive = diff * 3;
        generateShips(shipsAlive);
    }

    public Board getBoard() {
        return board;
    }

    public int getShipsAlive() {
        return shipsAlive;
    }

    public void setShipsAlive(int shipsAlive) {
        this.shipsAlive = shipsAlive;
    }

    private void generateShips (int numOfShips) {
        int direction, x, y, firstX, firstY;
        Random r = new Random();
        boolean okToPlace, placed;
        for (int i = 0; i < numOfShips * 3; i++) {
            Ship ship = new Ship(r.nextInt(4));
            direction = r.nextInt(2); // 0 = horizontal , 1 = vertical
            placed = false;
            while (!placed) {
                okToPlace = true;
                firstX = x = r.nextInt(Board.BOARD_SIZE);
                firstY = y = r.nextInt(Board.BOARD_SIZE);
                for (int k = 0; k < ship.getSize() && okToPlace; k++) {
                    if (board.getBoardMatrix()[x][y].isPlaced())      //tile already taken
                        okToPlace = false;
                    else {                                  // tile was free
                        if (direction == 0)
                            x++;
                        else
                            y++;
                    }
                }
                x = firstX;
                y = firstY;
                // the chip can be placed
                for (int k = 0; k < ship.getSize(); k++) {
                    board.getBoardMatrix()[x][y].setPlaced(ship);
                    if (direction == 0)
                        x++;
                    else
                        y++;
                }
                placed = true;
            }

        }
    }

}
