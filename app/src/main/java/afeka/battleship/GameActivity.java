package afeka.battleship;

import android.content.Intent;
import android.media.MediaPlayer;
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
    private int difficulty;
    private MediaPlayer playSoundHit;
    private MediaPlayer playSoundMiss;
    private MediaPlayer playSoundDrown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        difficulty = getIntent().getIntExtra(Game.DIFFICULTY, 3);
        game = new Game(difficulty);
        mainGrid = findViewById(R.id.gridView);
        buttonSwitch = findViewById(R.id.button_switchBoard);
        viewBoard = new TileAdapter(getApplicationContext());
        viewBoard.setmBoard(game.getBoard(Game.Players.PLAYER), Game.Players.PLAYER);
        mainGrid.setAdapter(viewBoard);
        currentPlayer = findViewById(R.id.playerText);
        statusGameToShow = findViewById(R.id.statusText);
        playSoundHit = MediaPlayer.create(getApplicationContext(), R.raw.pop);
        playSoundMiss = MediaPlayer.create(getApplicationContext(), R.raw.blup);
        playSoundDrown = MediaPlayer.create(getApplicationContext(), R.raw.splash);

        currentPlayer.setText(R.string.playerTurn);

        mainGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                enableGrid();
                playPlayer(position);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (currentGameStatus.equals(Game.GameStatus.MISS)) {
                            playComputer();
                        }
                        enableGrid();
                    }
                });
                t.start();

            }
        });
    }

    private void enableGrid() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mainGrid.isEnabled())
                    mainGrid.setEnabled(false);
                else
                    mainGrid.setEnabled(true);
            }
        });
    }

    private void playPlayer(int position) {
        currentGameStatus = game.playerPlay(position);
        updateBoard(Game.Players.PLAYER);
        if (currentGameStatus.equals(Game.GameStatus.WIN)) {
            winEndGame();
        } else {
            massageStatus(currentGameStatus, Game.Players.PLAYER);

        }
    }

    private void playComputer() {
        pause(2);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                statusGameToShow.setText("");
                currentPlayer.setText(R.string.computerTurn);
            }
        });
        updateBoard(Game.Players.COMPUTER);
        do {
            currentGameStatus = game.computerPlay(game.getBoard(Game.Players.COMPUTER));
            if (currentGameStatus.equals(Game.GameStatus.WIN)) {
                winEndGame();
                return;
            }

            updateBoard(Game.Players.COMPUTER);
            massageStatus(currentGameStatus, Game.Players.COMPUTER);
            pause(2);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    statusGameToShow.setText("");

                }
            });

        } while (game.getCurrentTurn().equals(Game.Players.COMPUTER));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                currentPlayer.setText(R.string.playerTurn);
            }
        });
        updateBoard(Game.Players.PLAYER);
    }

    private void updateBoard(final Game.Players p) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (p.equals(Game.Players.PLAYER))
                    viewBoard.setmBoard(game.getBoard(Game.Players.PLAYER), Game.Players.PLAYER);
                else
                    viewBoard.setmBoard(game.getBoard(Game.Players.COMPUTER), Game.Players.COMPUTER);
                ((TileAdapter) mainGrid.getAdapter()).notifyDataSetChanged();
            }
        });
    }


    private void massageStatus(final Game.GameStatus status, final Game.Players turn) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (status.equals(Game.GameStatus.HIT)) {
                    playSoundHit.start();
                    if (turn.equals(Game.Players.PLAYER))
                        statusGameToShow.setText(R.string.playerHit);
                    else
                        statusGameToShow.setText(R.string.computerHit);
                } else if (status.equals(Game.GameStatus.MISS)) {
                    playSoundMiss.start();
                    if (turn.equals(Game.Players.PLAYER))
                        statusGameToShow.setText(R.string.playerMiss);
                    else
                        statusGameToShow.setText(R.string.computerMiss);
                } else if (status.equals(Game.GameStatus.DROWN)) {
                    playSoundDrown.start();
                    statusGameToShow.setText(R.string.drowned);
                } else if (status.equals(Game.GameStatus.WRONG_MOVE))
                    statusGameToShow.setText(R.string.playerWrong);

            }
        });
    }

    private void winEndGame() {
        Game.Players whoWin = game.getCurrentTurn();
        Intent i = new Intent(this, EndActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Game.WHO_WIN, whoWin.toString());
        bundle.putInt(Game.DIFFICULTY, difficulty);

        i.putExtra(Game.END_BUNDLE, bundle);
        startActivity(i);
        finish();
    }


    public void pause(int i) { //stop for i sec the stimulate game
        try {
            Thread.sleep(i * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void switchBoard(View view) {
        /*
        if (boardToView.equals(Game.Players.PLAYER)) {
            boardToView = Game.Players.COMPUTER;
            buttonSwitch.setText(R.string.computerBoard);
        } else {
            boardToView = Game.Players.PLAYER;
            buttonSwitch.setText(R.string.playerBoard);
        }
        viewBoard.setmBoard(game.getBoard(boardToView), boardToView);
        ((TileAdapter) mainGrid.getAdapter()).notifyDataSetChanged();
        */
        game.getBoard(Game.Players.PLAYER).shuffleShips();
        viewBoard.setmBoard(game.getBoard(boardToView), boardToView);
        ((TileAdapter) mainGrid.getAdapter()).notifyDataSetChanged();
    }
}