package afeka.battleship;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import afeka.battleship.logic.Game;

public class MainActivity extends AppCompatActivity {
    private int difficulty;
    private ImageView playButton;
    private Animation anim;
    private ImageView easyButton;
    private ImageView mediumButton;
    private ImageView hardButton;
    private MediaPlayer selectSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = findViewById(R.id.play_button);
        playButton.setVisibility(View.INVISIBLE);
        anim = AnimationUtils.loadAnimation(this, R.anim.blink);
        easyButton = findViewById(R.id.easy_button);
        mediumButton = findViewById(R.id.medium_button);
        hardButton = findViewById(R.id.hard_button);

        selectSound = MediaPlayer.create(this, R.raw.click);
    }


    public void clickStart(View view) {
        selectSound.start();
        switch (view.getId()) {
            case R.id.easy_button:
                difficulty = 3;
                easyButton.startAnimation(anim);
                mediumButton.clearAnimation();
                hardButton.clearAnimation();
                break;
            case R.id.medium_button:
                difficulty = 2;
                mediumButton.startAnimation(anim);

                hardButton.clearAnimation();
                easyButton.clearAnimation();
                break;
            case R.id.hard_button:
                difficulty = 1;
                hardButton.startAnimation(anim);
                mediumButton.clearAnimation();
                easyButton.clearAnimation();
                break;
            default:
                difficulty = 3;
                break;
        }

        playButton.setVisibility(View.VISIBLE);


    }

    public void clickPlay(View view) {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra(Game.DIFFICULTY, difficulty);
        startActivity(i);
    }


}
