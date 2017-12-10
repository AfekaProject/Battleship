package afeka.battleship.View;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import afeka.battleship.Model.Board;
import afeka.battleship.Model.Tile;
import afeka.battleship.R;
import afeka.battleship.logic.Game;

public class TileAdapter extends BaseAdapter {

    private Context context;
    private Board mBoard;
    private Game.Players playerToView;

    public TileAdapter(Context context) {
        this.context = context;
    }

    public void setmBoard(Board mBoard,Game.Players playerToView) {
        this.playerToView = playerToView;
        this.mBoard = mBoard;
    }

    @Override
    public int getCount() {
        return mBoard.getSize();
    }

    @Override
    public Object getItem(int position) {
        return mBoard.getTile(position);
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
        Tile.Status status = mBoard.getTile(i).getStatus();

        switch (status) {
            case NONE:
                tileView.img.setImageResource(R.drawable.img_empty);
                break;
            case PLACED:
                if (playerToView.equals(Game.Players.PLAYER))
                    tileView.img.setImageResource(R.drawable.img_empty);
                else
                    tileView.img.setImageResource(R.drawable.img_hit);
                break;
            case HIT:
                tileView.img.setImageResource(R.drawable.img_hit);
                break;
            case MISS:
                tileView.img.setImageResource(R.drawable.img_miss);
                break;
            case DROWNED:
                tileView.img.setImageResource(R.drawable.img_dead_fish);
                break;
            default:
                tileView.img.setImageResource(R.drawable.img_empty);
                break;
        }
       // String text = mBoard.getTile(i).toString();

        //disable this line to view enemy ship for debug
  //      text = hideShips(text);

        //tileView.text.setText(text);

            return tileView;
    }

    private String hideShips (String text){
         if (playerToView.equals(Game.Players.PLAYER) && text.contains("S"))
             return "";
         return text;


    }
}
