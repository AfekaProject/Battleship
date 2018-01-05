package afeka.battleship;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import afeka.battleship.logic.Game;


public class EndActivity extends AppCompatActivity {
    private String whoWin;
    private int difficulty;
    private RelativeLayout relativeLayout;
    private ImageView title;
    MediaPlayer endSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSCREEN();
        if (whoWin.equals(Game.Players.PLAYER.toString()))
            winScreen();
        else
            loseScreen();
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

    private void initSCREEN(){
        setContentView(R.layout.activity_end);
        Bundle bundle = getIntent().getBundleExtra(Game.END_BUNDLE);
        whoWin = bundle.getString(Game.WHO_WIN);
        difficulty = bundle.getInt(Game.DIFFICULTY);
        relativeLayout = findViewById(R.id.relativeLayout);
        title = findViewById(R.id.winLoseTitle);
    }
    private void winScreen(){
        relativeLayout.setBackgroundResource(R.drawable.youwinbackground);
        title.setImageResource(R.drawable.youwintitle);
        endSound = MediaPlayer.create(this, R.raw.win);
        endSound.start();

        ImageView redFish = findViewById(R.id.redFish);
        redFish.setVisibility(View.VISIBLE);
        Animation moveRight = AnimationUtils.loadAnimation(this,R.anim.moveright);
        redFish.startAnimation(moveRight);

        ImageView blueFish = findViewById(R.id.blueFish);
        Animation moveLeft = AnimationUtils.loadAnimation(this,R.anim.moveleft);
        blueFish.setVisibility(View.VISIBLE);
        blueFish.startAnimation(moveLeft);
    }

    private void loseScreen(){
        relativeLayout.setBackgroundResource(R.drawable.youlosebackground);
        title.setImageResource(R.drawable.youlosetitle);
        endSound = MediaPlayer.create(this, R.raw.loser);
        endSound.start();

        ImageView shark = findViewById(R.id.shark);
        Animation zoomIn = AnimationUtils.loadAnimation(this,R.anim.zoomin);
        shark.setVisibility(View.VISIBLE);
        shark.startAnimation(zoomIn);
    }
}
