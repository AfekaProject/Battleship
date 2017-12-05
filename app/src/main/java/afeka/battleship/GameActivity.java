package afeka.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class GameActivity extends AppCompatActivity {

    private GridView mainGrid;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        game = new Game(1);
        mainGrid = (GridView)findViewById(R.id.gridview);

       // mainGrid.setAdapter(new TileAdapter());

    }









}
