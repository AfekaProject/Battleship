package afeka.battleship.Model;


import java.util.LinkedList;
import java.util.Random;

public class Fleet {

    private final int BOARD_SIZE =100;
    private Board logicBoard;
    private LinkedList<ShipNode> [] locations;
    private int[] countLiveNodes;

    public Fleet (int diff){
        logicBoard = new Board(BOARD_SIZE);

        //  generate(diff);
    }

    public Board getLogicBoard() {
        return logicBoard;
    }

    private void generate (int diff){
        Random r = new Random();
        for (int i =0 ; i<diff*5 ; i++){
            int temp =r.nextInt(100);
            // if the tile is empty
            if (logicBoard.getTile(temp).getStatus().equals(Board.TileStatus.NONE)){
                logicBoard.setTile(temp , Board.TileStatus.PLACED);

            }

        }

    }



}
