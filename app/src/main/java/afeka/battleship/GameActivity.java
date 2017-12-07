package afeka.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import afeka.battleship.View.TileAdapter;
import afeka.battleship.logic.Game;

public class GameActivity extends AppCompatActivity {

    public GridView mainGrid;
    private Game game;
    private TextView currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //int  difficulty = savedInstanceState.getInt("Difficulty");
        int  difficulty = 3;
        game = new Game(difficulty);
        mainGrid = findViewById(R.id.gridView);
        TileAdapter viewBoard = new TileAdapter(getApplicationContext());
        viewBoard.setmBoard(game.getBoard(Game.Turn.PLAYER));
        mainGrid.setAdapter(viewBoard);
        ((TileAdapter) mainGrid.getAdapter()).notifyDataSetChanged();
        currentPlayer = findViewById(R.id.playerText);
        currentPlayer.setText("Your Turn");

        mainGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

//                game.playerPlay(position);


                ((TileAdapter) mainGrid.getAdapter()).notifyDataSetChanged();
/*

                if (game.getWhosTurn().equals(Game.Turn.PLAYER)) {
                    ((TextView) findViewById(R.id.playerText)).setText("Your Turn");
                    game.playGame(position);
                } else {
                    ((TextView) findViewById(R.id.playerText)).setText("Computer's Turn");
                    game.computerPlay();
                }
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        game.computerPlay();
                    }
                });*/
            }


        });
    }
}