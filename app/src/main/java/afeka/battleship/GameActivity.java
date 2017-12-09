package afeka.battleship;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

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
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                playPlayer(position);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (currentGameStatus.equals(Game.GameStatus.MISS)) {
                            playComputer();
                        }

                    }
                });
                t.start();
            }
        });
    }

    private void enableGrid(){
        if(mainGrid.isEnabled())
            mainGrid.setEnabled(false);
        else
            mainGrid.setEnabled(true);
    }

    private void playPlayer(final int position){
        currentGameStatus = game.playerPlay(position);
        if(currentGameStatus.equals(Game.GameStatus.WIN)) {
            winEndGame(Game.Players.PLAYER);
         }else {
            //massageStatus(currentGameStatus, Game.Players.PLAYER);
            updateBoard(Game.Players.PLAYER);
            pause(1);
            //statusGameToShow.setText("");
        }
    }

    private void playComputer(){
        enableGrid();
        pause(2);
        currentPlayer.setText(R.string.computerTurn);
        updateBoard(Game.Players.COMPUTER);
        do {
            currentGameStatus=game.computerPlay();
            if(currentGameStatus.equals(Game.GameStatus.WIN)) {
                winEndGame(Game.Players.PLAYER);
                break;
            }
           // massageStatus(currentGameStatus, Game.Players.COMPUTER);
            updateBoard(Game.Players.COMPUTER);
            pause(1);
            //statusGameToShow.setText("");
            pause(1);
        } while(game.getCurrentTurn().equals(Game.Players.COMPUTER));
        currentPlayer.setText(R.string.playerTurn);
        updateBoard(Game.Players.PLAYER);
        enableGrid();
    }

    private void updateBoard(final Game.Players p) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (p.equals(Game.Players.PLAYER))
                    viewBoard.setmBoard(game.getBoard(Game.Players.PLAYER),Game.Players.PLAYER);
                else
                    viewBoard.setmBoard(game.getBoard(Game.Players.COMPUTER),Game.Players.COMPUTER);
                ((TileAdapter) mainGrid.getAdapter()).notifyDataSetChanged();
            }
        });
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

    private void winEndGame(Game.Players whoWin){

        Intent i = new Intent(this,EndActivity.class);
      /*  Bundle bundle = new Bundle();
        bundle.putString("whoWin",whoWin.toString());
        bundle.putInt("DIFFICULTY",difficulty);

        i.putExtra("WIN+DIFF",bundle);*/
        startActivity(i);
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