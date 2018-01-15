package afeka.battleship.View;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
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

    public void setmBoard(Board mBoard, Game.Players playerToView) {
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

        if (view == null) {
            tileView = new TileView(context);
        } else {
            tileView = (TileView) view;
        }
        Tile currentTile = mBoard.getTile(i);
        Tile.Status status = currentTile.getStatus();
        int size = viewGroup.getWidth() / 10 - 15;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);
        tileView.getImg().setLayoutParams(layoutParams);
        tileView.setLayoutParams(layoutParams);
        tileView.setBackgroundResource(R.color.colorPrimary);
        switch (status) {
            case NONE:
                tileView.getImg().setImageResource(R.drawable.img_empty);
                tileView.setBackgroundResource(R.color.colorPrimary);
                break;
            case PLACED:
                tileView.getImg().setImageResource(R.drawable.img_goldfish);
                //disable this line to view enemy ship for debug
                if (!Game.debug)
                    hideShips(tileView);
                break;
            case HIT:
                if(!currentTile.isWasHitAnimated()) {
                    tileView.getImg().setImageResource(R.drawable.img_empty);
                    tileView.setBackgroundResource(R.drawable.explosionsprite);
                    AnimationDrawable animationDrawable = (AnimationDrawable) tileView.getBackground();
                    animationDrawable.start();
                    tileView.getImg().setBackgroundResource(R.color.tranparent);


                    currentTile.setWasHitAnimated(true);

                }else{

                    tileView.getImg().setImageResource(R.drawable.img_hit);
                    tileView.setBackgroundResource(R.color.colorPrimary);

                }



                break;
            case MISS:
                tileView.getImg().setImageResource(R.drawable.img_miss);
                break;
            case DROWNED:
                if(!currentTile.isWasDrawnAnimated()){
                    tileView.getImg().setImageResource(R.drawable.img_empty);

                    tileView.setBackgroundResource(R.drawable.watersprite);

                    AnimationDrawable animationDrawable = (AnimationDrawable) tileView.getBackground();
                    animationDrawable.start();
                    tileView.getImg().setBackgroundResource(R.color.tranparent);
                    currentTile.setWasDrawnAnimated(true);
                }else {
                    tileView.setBackgroundResource(R.color.tranparent);
                    tileView.getImg().setImageResource(R.drawable.img_deadfish);
                }
                break;
            default:
                tileView.getImg().setImageResource(R.drawable.img_empty);
                break;
        }

        return tileView;
    }

    private void hideShips(TileView tileView) {
        if (playerToView.equals(Game.Players.PLAYER))
            tileView.getImg().setImageResource(R.drawable.img_empty);
        else
            tileView.getImg().setImageResource(R.drawable.img_goldfish);
    }

}
