package afeka.battleship.View;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import afeka.battleship.R;

public class TileView extends FrameLayout {

    ImageView img;
    public TileView(Context context) {
        super(context);
        //put here setting for how to view the tile in grid
        img = new ImageView(context);
        //LayoutParams layoutParams = new FrameLayout.LayoutParams(90,90);
        //img.setLayoutParams(layoutParams);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        addView(img);
    }





}
