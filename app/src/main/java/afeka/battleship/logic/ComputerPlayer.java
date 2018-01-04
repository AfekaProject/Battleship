package afeka.battleship.logic;

import java.util.ArrayList;
import java.util.Random;

import afeka.battleship.Model.Board;

public class ComputerPlayer {
    private ArrayList<Integer> places;
    private int lastPosition = -1;

    public ComputerPlayer() {
        places = new ArrayList<>();
        for (int i = 0; i < Board.BOARD_SIZE * Board.BOARD_SIZE; i++)
            places.add(i);
    }

    private void think() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int playTurn(Board board) {
        think();
        Random random = new Random();
        int r = random.nextInt(places.size());
        int temp = places.get(r);
        places.remove(r);
        // make ai win (for debug)  disable for game
        if (Game.aiWin)
            temp = cpuAutoWin(board);

        lastPosition = temp;
        return temp;
    }

    private int cpuAutoWin(Board board){       // ai win  use for debug
        boolean found=false;
        int i;
        for (i = 0 ; (i<Board.BOARD_SIZE*Board.BOARD_SIZE)&& !found; ) {
            if (board.getTile(i).isPlaced())
                found = true;
            else
                i++;
        }
        return i;
    }

    public int getLastPosition(){
        return lastPosition;
    }
}