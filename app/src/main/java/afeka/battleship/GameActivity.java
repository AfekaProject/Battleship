package afeka.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import afeka.battleship.View.TileAdapter;
import afeka.battleship.logic.Game;

public class GameActivity extends AppCompatActivity {

    private GridView mainGrid;
    private Button buttonSwitch;
    private Game game;
    private TextView currentPlayer;
    private TextView statusGameToShow;
    private Game.GameStatus currentGameStatus;
    private Game.Players boardToView = Game.Players.PLAYER;
    private TileAdapter viewBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        game = new Game(3);
        int difficulty =getIntent().getIntExtra("Difficulty",3);
        game = new Game(difficulty);
        mainGrid = findViewById(R.id.gridView);
        buttonSwitch = findViewById(R.id.button_switchBoard);
        viewBoard = new TileAdapter(getApplicationContext());
        viewBoard.setmBoard(game.getBoard(Game.Players.PLAYER),Game.Players.PLAYER);
        mainGrid.setAdapter(viewBoard);
        currentPlayer = findViewById(R.id.playerText);
        statusGameToShow = findViewById(R.id.statusText);

        currentPlayer.setText(R.string.playerTurn);

        mainGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                    currentGameStatus = game.playerPlay(position);
                    massageStatus(currentGameStatus, Game.Players.PLAYER);
                    ((TileAdapter) mainGrid.getAdapter()).notifyDataSetChanged();


                enableGrid();
                pause(3);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                    while(game.getCurrentTurn().equals(Game.Players.COMPUTER)){
                            currentPlayer.setText(R.string.computerTurn);
                            currentGameStatus=game.computerPlay();
                            massageStatus(currentGameStatus, Game.Players.COMPUTER);
                            statusGameToShow.setText("");

                    }
                        currentPlayer.setText(R.string.playerTurn);
                    }
                });
                t.start();
                enableGrid();

            }


        });
    }

    private void enableGrid(){
        if(mainGrid.isEnabled())
            mainGrid.setEnabled(false);
        else
            mainGrid.setEnabled(true);
    }

    private void massageStatus(Game.GameStatus status, Game.Players turn) {
        if (status.equals(Game.GameStatus.HIT)) {
            if (turn.equals(Game.Players.PLAYER))
                statusGameToShow.setText(R.string.playerHit);
            else
                statusGameToShow.setText(R.string.computerHit);
        } else if (status.equals(Game.GameStatus.MISS)) {
            if (turn.equals(Game.Players.PLAYER))
                statusGameToShow.setText(R.string.playerMiss);
            else
                statusGameToShow.setText(R.string.computerMiss);
        } else if (status.equals(Game.GameStatus.WRONG_MOVE))
            statusGameToShow.setText(R.string.playerWrong);
    }

    public void pause(int i){ //stop for i sec the stimulate game
        try {
            Thread.sleep(i*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void switchBoard(View view) {
        if (boardToView.equals(Game.Players.PLAYER)){
            boardToView = Game.Players.COMPUTER;
            buttonSwitch.setText(R.string.computerBoard);
        }

        else{
            boardToView = Game.Players.PLAYER;
            buttonSwitch.setText(R.string.playerBoard);
        }
        viewBoard.setmBoard(game.getBoard(boardToView), boardToView);
        ((TileAdapter) mainGrid.getAdapter()).notifyDataSetChanged();
    }
}