package afeka.battleship.logic;

import java.util.Random;

import afeka.battleship.Model.Board;

public class ComputerPlayer {

    private void think() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int playTurn(Board board) {
        int x,y;
        think();

        Random random = new Random();

        do{
            x = random.nextInt(Board.BOARD_SIZE);
            y = random.nextInt(Board.BOARD_SIZE);
        } while (!board.getBoardMatrix()[x][y].isFreeToClick());

        return (x*Board.BOARD_SIZE)+y;

    }
}
