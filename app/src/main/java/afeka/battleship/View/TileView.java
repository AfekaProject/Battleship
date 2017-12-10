package afeka.battleship.View;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import afeka.battleship.R;

public class TileView extends FrameLayout {

    TextView text;

    public TileView(Context context) {
        super(context);
        //put here setting for how to view the tile in grid
        text = new TextView(context);


        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        text.setLayoutParams(layoutParams);
        text.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        text.setGravity(Gravity.CENTER_VERTICAL);
        text.setTextSize(20);
        text.setTextColor(Color.BLACK);
        text.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        //alinge color or add image (change to img VIew insted of textView
        addView(text);


    }



}
