package afeka.battleship.Model;

public class Tile {
    public enum status {NONE,HIT,MISS,PLACED}
    private int x;
    private int y;
 //   private boolean isHit;
    private Ship ship;
    private status status;

    public Tile(int x , int y){
        this.x = x;
        this.y = y;
     //   isHit = false;
        status = status.NONE;

    }
    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public status setStatus() {
        return status;
    }

    public boolean isHit() {
        if(status.equals(status.HIT))
            return true;
        else
            return false;
    }

    public void setHit() {
        status = status.HIT;
        ship.setHit(); //update ship's hit counter
    }

    public void setMiss() {
        status = status.MISS;
    }

    public void setPlaced() {
        status = status.PLACED;
    }


  /*  public void setHitTileInShip() {
        isHit = true;
        ship.setHit();
    }*/
}
    /*
    private Board.TileStatus status;

    Tile(){
        this(Board.TileStatus.NONE);
    }

    private Tile (Board.TileStatus status){
        setStatus(status);
    }
    public Board.TileStatus getStatus() {
        return status;
    }

    void setStatus(Board.TileStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        switch (status) {
            case NONE:
                return "";
            case PLACED:
                return "";
            case HIT:
                return "X";
            case MISS:
                return "~";

            default:
                return "";
        }
    }*/
}
