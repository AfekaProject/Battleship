package afeka.battleship;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import afeka.battleship.logic.Game;


public class EndActivity extends AppCompatActivity {
    private String whoWin;
    private int difficulty;
    private RelativeLayout relativeLayout;
    private ImageView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        Bundle bundle = getIntent().getBundleExtra(Game.END_BUNDLE);
        whoWin = bundle.getString(Game.WHO_WIN);
        difficulty = bundle.getInt(Game.DIFFICULTY);
        relativeLayout = findViewById(R.id.relativeLayout);
        title = findViewById(R.id.winLoseTitle);
        setBackgroundAndSound();


    }

    private void setBackgroundAndSound() {

        MediaPlayer endSound;

        if (whoWin.equals(Game.Players.PLAYER.toString())) {
            relativeLayout.setBackgroundResource(R.drawable.youwinbackground);
            title.setImageResource(R.drawable.youwintitle);
            endSound = MediaPlayer.create(this, R.raw.win);
        } else {
            relativeLayout.setBackgroundResource(R.drawable.youlosebackground);
            title.setImageResource(R.drawable.youlosetitle);
            endSound = MediaPlayer.create(this, R.raw.loser);
        }
        endSound.start();
    }


    public void newGameClick(View view) {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra(Game.DIFFICULTY, difficulty);
        startActivity(i);
        finish();

    }

    public void mainClick(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
