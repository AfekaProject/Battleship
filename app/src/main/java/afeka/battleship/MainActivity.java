package afeka.battleship;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private int difficulty;
    private ImageView playButton;
    private MediaPlayer selectSound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = findViewById(R.id.play_button);
        playButton.setVisibility(View.INVISIBLE);
        selectSound = MediaPlayer.create(this,R.raw.click);
    }


    public void clickStart(View view) {
        selectSound.start();
        switch (view.getId()) {
            case R.id.easy_button:
                difficulty = 3;
                break;
            case R.id.medium_button:
                difficulty = 2;
                break;
            case R.id.hard_button:
                difficulty = 1;
                break;
            default:
                difficulty = 3;
                break;
        }

        playButton.setVisibility(View.VISIBLE);


    }

    public void clicklPlay(View view) {
        Intent i = new Intent(this,GameActivity.class);
        i.putExtra("Difficulty", difficulty);
        startActivity(i);

    }
}
