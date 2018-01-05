package afeka.battleship.View;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoreView extends LinearLayout{

    private TextView name;
    private TextView score;
    private TextView date;

    public ScoreView(Context context) {
        super(context);
        name = new TextView(context);
        name.setTextSize(20);
        score = new TextView(context);
        score.setTextSize(20);
        date = new TextView(context);
        date.setTextSize(20);

       // LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(550,50);

        //layoutParams.gravity=Gravity.LEFT;
        //name.setLayoutParams(layoutParams);
        //score.setGravity(Gravity.CENTER_HORIZONTAL);
        //layoutParams.gravity=Gravity.CENTER;
        //layoutParams.setMargins(300,0,100,0);
        //score.setLayoutParams(layoutParams);

        //layoutParams.gravity=Gravity.RIGHT;
        //date.setLayoutParams(layoutParams);

        addView(name);
        addView(score);
        addView(date);
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public void setScore(TextView score) {
        this.score = score;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public TextView getName() {
        return name;
    }

    public TextView getScore() {
        return score;
    }

    public TextView getDate() {
        return date;
    }
}
