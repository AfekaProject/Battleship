package afeka.battleship.logic;


public class LogicBoard {
    private enum status{NONE,HIT,MISS,PLACED}
    private final int BOARD_SIZE = 10;
    private status logicBoard[][];


    public LogicBoard(int diff){
        logicBoard = new status[BOARD_SIZE][BOARD_SIZE];

        for(int i=0 ; i<BOARD_SIZE ; i++){
            for(int j=0 ; j<BOARD_SIZE ; j++){
                logicBoard[i][j] = status.NONE;
            }
        }

    }

    public void setHitStatus(int position){
            logicBoard [positionToX(position)][positionToY(position)] = status.HIT;
    }
    public void setMissStatus(int position){
        logicBoard [positionToX(position)][positionToY(position)] = status.MISS;
    }
    public void setPlacedStatus(int position){
        logicBoard [positionToX(position)][positionToY(position)] = status.PLACED;
    }

    private int positionToX(int position){ //convert position to x
        return position % BOARD_SIZE;
    }
    private int positionToY(int position){ //convert position to y
        return position / BOARD_SIZE;
    }


    public status[][] getLogicBoard() {
        return logicBoard;
    }

  /*  private void generate (int diff){
        Random r = new Random();
        for (int i =0 ; i<diff*5 ; i++){
            int temp =r.nextInt(100);
            // if the tile is empty
            if (logicBoard.getTile(temp).getStatus().equals(Board.TileStatus.NONE)){
                logicBoard.setTile(temp , Board.TileStatus.PLACED);

            }

        }

    }*/



}
