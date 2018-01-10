package afeka.battleship.View;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.zip.Inflater;
import android.view.LayoutInflater;
import afeka.battleship.Model.Score;
import afeka.battleship.R;

public class ScoreAdapter extends BaseAdapter {

    private Context context;
    private Score[] scoreList;
    LayoutInflater inflater;
    public ScoreAdapter(Context context) {
        this.context = context;
    }

    public ScoreAdapter(Context context, Score[] scoreList) {
        this.context = context;
        this.scoreList = scoreList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setScoreList(Score[] scoreList) {
        this.scoreList = scoreList;
    }

    @Override
    public int getCount() {
        return scoreList.length;
    }

    @Override
    public Object getItem(int position) {
        return scoreList[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
   /*     ScoreView scoreView;
        if (convertView== null) {
            scoreView = new ScoreView(context);
        } else {
            scoreView = (ScoreView) convertView;
        }

        scoreView.getName().setText(scoreList[position].getName());
        scoreView.getScore().setText(scoreList[position].getScore()+"");
        String date = scoreList[position].getDate()+"."+(scoreList[position].getDate().getMonth()+1);
        scoreView.getDate().setText(date);

        return scoreView;
        */
        View itemLayout;
        if (convertView == null) {
            itemLayout = inflater.inflate(R.layout.list_item, null, false);
        } else {
            itemLayout = convertView;
        }
        //do what you want with itemLayout;
        return itemLayout;

    }


}
