package afeka.battleship;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Date;
import afeka.battleship.Model.Score;
import afeka.battleship.logic.Game;

public class EndActivity extends AppCompatActivity {
    private String whoWin;
    private int difficulty;
    private int currentScore;
    private RelativeLayout relativeLayout;
    private ImageView title;
    private MediaPlayer endSound;
    private Database database;
    private ImageView highScoreLabel;
    private EditText editText;
    private ImageView submitButton;
    private LatLng location;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSCREEN();
        if (whoWin.equals(Game.Players.PLAYER.toString())) {
            initPlayerInfo();
            winScreen();
        }else
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
        editText = findViewById(R.id.textPlayerName);
        submitButton = findViewById(R.id.saveButton);
        highScoreLabel = findViewById(R.id.highscorelabel);
    }

    private void initPlayerInfo(){
        database = new Database(this);
        Bundle locationBundle = getIntent().getBundleExtra(Game.LOCATION_BUNDLE);
        location = new LatLng(locationBundle.getDouble(Game.LAT), locationBundle.getDouble(Game.LONG));
        Bundle scoreBundle = getIntent().getBundleExtra(Game.SCORE_BUNDLE);
        currentScore = scoreBundle.getInt(Game.SCORE);
    }
    private void winScreen(){
        relativeLayout.setBackgroundResource(R.drawable.youwinbackground);
        editText = findViewById(R.id.textPlayerName);
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


        if(!checkIfHighScore()) {
            editText.setVisibility(View.INVISIBLE);
            submitButton.setVisibility(View.GONE);
            highScoreLabel.setVisibility(View.INVISIBLE);
        }
    }

    private void loseScreen(){

        relativeLayout.setBackgroundResource(R.drawable.youlosebackground);
        title.setImageResource(R.drawable.youlosetitle);
        endSound = MediaPlayer.create(this, R.raw.loser);
        endSound.start();

        editText.setVisibility(View.INVISIBLE);
        submitButton.setVisibility(View.GONE);
        highScoreLabel.setVisibility(View.INVISIBLE);

        ImageView shark = findViewById(R.id.shark);
        Animation zoomIn = AnimationUtils.loadAnimation(this,R.anim.zoomin);
        shark.setVisibility(View.VISIBLE);
        shark.startAnimation(zoomIn);
    }

    private boolean checkIfHighScore(){
       ArrayList<Score> allScores = database.getScoreList(difficulty);

        if(allScores.size()<10 || allScores.get(allScores.size()-1).getScore()< currentScore)
            return true;
        else
            return false;
    }

    private void setHighScore(String name, LatLng latLng){
        Date currentDate = new Date();
        Score newScore = new Score(name,difficulty,currentScore,currentDate,latLng);
        database.addScores(newScore);

    }

    public void submitButtonClicked(View view) {
        String name = editText.getText().toString();
        Log.e("name",""+name);
        setHighScore(name,location);
        View tempView = this.getCurrentFocus();
        if (tempView !=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(tempView.getWindowToken(),0);
        }
        view.setVisibility(View.GONE);
        editText.setVisibility(View.INVISIBLE);

    }
}
