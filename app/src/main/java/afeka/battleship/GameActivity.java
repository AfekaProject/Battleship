package afeka.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import afeka.battleship.View.TileAdapter;
import afeka.battleship.logic.Game;

public class GameActivity extends AppCompatActivity {

    private GridView mainGrid;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        game = new Game(1);
        mainGrid = findViewById(R.id.gridview);
        TileAdapter viewBoard = new TileAdapter(getApplicationContext());
        viewBoard.setmBoard(game.getbPlayerToShow());
        mainGrid.setAdapter(viewBoard);

    }









}
