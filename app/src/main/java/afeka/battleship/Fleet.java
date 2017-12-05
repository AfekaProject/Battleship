package afeka.battleship;


import java.util.LinkedList;
import java.util.Random;

public class Fleet {

    private Board logicBoard;
    private LinkedList<ShipNode> [] locations;
    private int[] countLiveNodes;

    public Fleet (int diff){
          generate(diff);
    }

    private void generate (int diff){
        Random r = new Random();
        for (int i =0 ; i<diff*5 ; i++){
            int temp =r.nextInt(100);
            // if the tile is empty
            if (logicBoard.getTileInPosition(temp).getStatus().equals(Board.tileStatus.None)){
                logicBoard.setTileInPosition(temp , Board.tileStatus.Placed);

            }

        }

    }



}
