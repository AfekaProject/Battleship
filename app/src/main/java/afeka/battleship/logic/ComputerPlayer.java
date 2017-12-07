package afeka.battleship.logic;

import java.util.Random;

import afeka.battleship.Model.Board;

public class ComputerPlayer {

    public void think() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int playTurn(Board board) {
        //need to change
        think();

        Random random = new Random();

        int positionToPlay = random.nextInt(9);

        while(board.getTile(positionToPlay).getStatus() != Board.TileStatus.NONE) {

            positionToPlay = random.nextInt(9);

        }

        return positionToPlay;
    }
}
