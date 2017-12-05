package afeka.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import afeka.battleship.View.TileAdapter;
import afeka.battleship.logic.Game;

public class GameActivity extends AppCompatActivity {

    private GridView mainGrid;
    private Game game;
    private TextView currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        game = new Game(1);
        mainGrid = findViewById(R.id.gridView);
        TileAdapter viewBoard = new TileAdapter(getApplicationContext());
        viewBoard.setmBoard(game.getbPlayerToShow());
        mainGrid.setAdapter(viewBoard);
        currentPlayer = findViewById(R.id.playerText);
        currentPlayer.setText("Your Turn");


    }









}
