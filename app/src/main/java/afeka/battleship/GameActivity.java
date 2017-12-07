package afeka.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import afeka.battleship.View.TileAdapter;
import afeka.battleship.logic.Game;
import android.widget.AdapterView;
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
    //    currentPlayer = findViewById(R.id.playerText);
    //    currentPlayer.setText("Your Turn");

        mainGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                ((TileAdapter)mainGrid.getAdapter()).notifyDataSetChanged();


                    if(game.getWhosTurn().equals(Game.turn.PLAYER)) {
                        ((TextView)findViewById(R.id.playerText)).setText("Your Turn");
                        game.playGame(position);
                    }else {
                        ((TextView)findViewById(R.id.playerText)).setText("Computer's Turn");
                        game.computerPlay();
                    }
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        game.playComputer();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                          //      Toast.makeText(getApplicationContext(),"Computer Finihsed his turn",Toast.LENGTH_LONG).show();
                                ((TileAdapter) mainGrid.getAdapter()).notifyDataSetChanged();
                                ((TextView)findViewById(R.id.playerText)).setText("Player's Turn");
                             //   ((ProgressBar)findViewById(R.id.progressBar)).setVisibility(View.INVISIBLE);

                            }
                        });
                    }
                });

                t.start();








            }
        });


        }




}
