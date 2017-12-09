package afeka.battleship;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import afeka.battleship.logic.Game;


public class EndActivity extends AppCompatActivity {
    private Bundle savedInstanceState;
    private String whoWin;
    private int difficulty;
    private TextView winLoseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        Bundle bundle = getIntent().getBundleExtra("WIN+DIFF");
        whoWin = bundle.getString("WhoWin");
        difficulty = bundle.getInt("Difficulty");
        winLoseText = findViewById(R.id.winLoseTitle);
        setWinLoseText(whoWin);

  //      whoWin = getIntent().getExtras().getString("WhoWin");
  //      difficulty = getIntent().getExtras().getInt("Difficulty");


    }

    public void setWinLoseText(String whoWin){
        if(whoWin.equals(Game.Players.PLAYER.toString()))
            winLoseText.setText(R.string.win_title);
        else
            winLoseText.setText(R.string.lose_title);

    }

    public void newGameClick(View view) {
        Intent i = new Intent(this,GameActivity.class);
        i.putExtra("Difficulty",difficulty);
        startActivity(i);

    }

    public void mainClick(View view) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
