package afeka.battleship;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



public class EndActivity extends AppCompatActivity {
    private Bundle savedInstanceState;
    private String whoWin;
    private int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        whoWin = getIntent().getExtras().getString("WhoWin");
        difficulty = getIntent().getExtras().getInt("Difficulty");



    }

    public void newGameClick(View view) {
        Intent i = new Intent(this,EndActivity.class);
        i.putExtra("Difficulty",difficulty);
        startActivity(i);

    }

    public void mainClick(View view) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
