package afeka.battleship;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import afeka.battleship.logic.Game;


public class EndActivity extends AppCompatActivity {
    private int difficulty;
    private TextView winLoseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        Bundle bundle = getIntent().getBundleExtra("WIN+DIFF");
        String whoWin = bundle.getString("WhoWin");
        difficulty = bundle.getInt("Difficulty");
        winLoseText = findViewById(R.id.winLoseTitle);
        setWinLoseText(whoWin);
    }

    public void setWinLoseText(String whoWin){
        MediaPlayer endSound;
        if(whoWin.equals(Game.Players.PLAYER.toString())){
            winLoseText.setText(R.string.win_title);
            endSound = MediaPlayer.create(this,R.raw.win);
        }
        else{
            winLoseText.setText(R.string.lose_title);
            endSound = MediaPlayer.create(this,R.raw.loser);
        }
        endSound.start();

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
