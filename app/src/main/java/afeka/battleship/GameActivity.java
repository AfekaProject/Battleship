package afeka.battleship;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import afeka.battleship.View.TileAdapter;
import afeka.battleship.logic.Game;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.content.ComponentName;

public class GameActivity extends AppCompatActivity implements GameService.TimerListener, GameService.MySensorListener {

    private GridView mainGrid;
    private Button buttonSwitch;
    private ProgressBar progressBar;
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
    private Animation slideUp;
    private Animation bold;
    private Vibrator v;
    private GameService.MyLocalBinder mBinder;
    private boolean isBound = false;
    private int countSensorEvent = 0;

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
        slideUp= AnimationUtils.loadAnimation(this,R.anim.slideup);
        bold= AnimationUtils.loadAnimation(this,R.anim.bold);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        v =  (Vibrator) getSystemService(VIBRATOR_SERVICE);
        currentPlayer.setText(R.string.playerTurn);


        mainGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l) {
                enableGrid();
                playPlayer(position);
                animateTile(view);
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
                progressBar.setVisibility(View.VISIBLE);

            }
        });
        updateBoard(Game.Players.COMPUTER);
        do {
            currentGameStatus = game.computerPlay(game.getBoard(Game.Players.COMPUTER));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int lastPosition =  game.getLastPosition();
                    View view = mainGrid.getChildAt(lastPosition);
                    animateTile(view);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

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
                    progressBar.setVisibility(View.VISIBLE);
                }
            });

        } while (game.getCurrentTurn().equals(Game.Players.COMPUTER));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
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

    private void animateTile (View view){
        if (currentGameStatus.equals(Game.GameStatus.MISS)){
            view.startAnimation(slideUp);
            v.vibrate(150);
        }
        else if (currentGameStatus.equals(Game.GameStatus.HIT))
            view.startAnimation(bold);
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

        if (boardToView.equals(Game.Players.PLAYER)) {
            boardToView = Game.Players.COMPUTER;
            buttonSwitch.setText(R.string.computerBoard);
        } else {
            boardToView = Game.Players.PLAYER;
            buttonSwitch.setText(R.string.playerBoard);
        }
        viewBoard.setmBoard(game.getBoard(boardToView), boardToView);
        ((TileAdapter) mainGrid.getAdapter()).notifyDataSetChanged();


        /*      code for shaffle test
        game.getBoard(Game.Players.PLAYER).shuffleShips();
        viewBoard.setmBoard(game.getBoard(boardToView), boardToView);
        ((TileAdapter) mainGrid.getAdapter()).notifyDataSetChanged();
        */
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, GameService.class);
        Log.d("On start", "binding to service...");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            mBinder.DeleteTimerListener();
            mBinder.DeleteSensorListener();
            unbindService(mConnection);
            isBound = false;
    }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("Service Connection", "bound to service");
            mBinder = (GameService.MyLocalBinder)service;
            mBinder.registerTimeListener(GameActivity.this);
            mBinder.registerSensorListener(GameActivity.this);
            Log.e("Service Connection", "registered as listener");
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            isBound = false;
        }
    };


    @Override
    public void timePassed() { //shuffle player ships every 10 second
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                game.getBoard(Game.Players.PLAYER).shuffleShips();

            }
        });

    }

    @Override
    public void moveChanged() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                countSensorEvent++;
                if(countSensorEvent == 5 ) {
                    game.getBoard(Game.Players.PLAYER).setRandomHit();
                 //   updateBoard(Game.Players.PLAYER); //only for checking!!
                    statusGameToShow.setText(R.string.intentHit);
                    countSensorEvent = 0;
                }
            }
        });
    }
}