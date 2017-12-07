package afeka.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void clickStart(View view) {
        int difficulty;
        switch (view.getId()) {
            case R.id.button_easy:
                difficulty = 3;
                break;
            case R.id.button_medium:
                difficulty = 2;
                break;
            case R.id.button_hard:
                difficulty = 1;
                break;
            default:
                difficulty = 3;
                break;
        }
        Intent i = new Intent(this,GameActivity.class);
        i.putExtra("Difficulty", difficulty);
        startActivity(i);

    }
}
