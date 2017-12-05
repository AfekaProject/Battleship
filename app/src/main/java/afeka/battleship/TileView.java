package afeka.battleship;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TileView extends LinearLayout {

    TextView text;

    public TileView(Context context) {
        super(context);
        //put here setting for how to view the tile in grid
        //alinge color or add image (change to img VIew insted of textView
        addView(text);
    }


}
