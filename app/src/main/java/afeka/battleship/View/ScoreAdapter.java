package afeka.battleship.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.zip.Inflater;
import android.view.LayoutInflater;
import android.widget.TextView;

import afeka.battleship.HighScoreFragment;
import afeka.battleship.Model.Score;
import afeka.battleship.R;

public class ScoreAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Score> scoreList;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;
    public ScoreAdapter(Context context) {
        this.context = context;
    }

    public ScoreAdapter(Context context, ArrayList<Score> scoreList) {
        this.context = context;
        this.scoreList = scoreList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return scoreList.size();
    }

    @Override
    public Object getItem(int position) {
        return scoreList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = convertView.findViewById(R.id.name);
            viewHolder.scoreTextView =  convertView.findViewById(R.id.score);
            viewHolder.dateTextView =  convertView.findViewById(R.id.date);

            convertView.setTag(viewHolder);
        } else {
           // itemLayout = convertView;
            viewHolder = (ViewHolder) convertView.getTag();

        }
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");


            viewHolder.nameTextView.setText(scoreList.get(position).getName());
            viewHolder.scoreTextView.setText(scoreList.get(position).getScore() + "");
            viewHolder.dateTextView.setText(df.format(scoreList.get(position).getDate()));

        //do what you want with itemLayout;
        return convertView;

    }

    static class ViewHolder {
        private TextView nameTextView;
        private TextView scoreTextView;
        private TextView dateTextView;
    }
}


