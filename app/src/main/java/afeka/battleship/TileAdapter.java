package afeka.battleship;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TileAdapter extends BaseAdapter {

    private Context context;
    private Board mBoard;

    public TileAdapter(Context context, Board mBoard) {
        this.context = context;
        this.mBoard = mBoard;
    }

    public void setmBoard(Board mBoard) {
        this.mBoard = mBoard;
    }

    @Override
    public int getCount() {
        return mBoard.getSize();
    }

    @Override
    public Object getItem(int position) {
        return mBoard.getTileInPosition(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TileView tileView;
        if (view == null){
            tileView = new TileView(context);
        }
        else{
            tileView = (TileView) view;
        }

        tileView.text.setText(mBoard.getTileInPosition(i).getStatus().toString());

            return tileView;
    }
}
