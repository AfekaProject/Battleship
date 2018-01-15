package afeka.battleship.View;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import afeka.battleship.R;

public class TileView extends FrameLayout {

    private ImageView img;
    public TileView(Context context) {
        super(context);
        img = new ImageView(context);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        addView(img);
    }

    public ImageView getImg() {
        return img;
    }
}
