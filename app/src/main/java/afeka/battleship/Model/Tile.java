package afeka.battleship.Model;

public class Tile {
    public enum Status {NONE,HIT,MISS,PLACED}
    private int x;
    private int y;
 //   private boolean isHit;
    private Ship ship;

    private Status status;

    public Tile(int x , int y){
        this.x = x;
        this.y = y;
     //   isHit = false;
        status = status.NONE;

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
    }
    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Status getStatus() {
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

    public void setPlaced(Ship ship) {
        setShip(ship);
        status = status.PLACED;
    }

    public boolean isFreeToClick(){
        return (status.equals(Status.PLACED) || status.equals(Status.NONE));
    }

    public boolean isPlaced(){
        return status.equals(Status.PLACED);
    }
  /*  public void setHitTileInShip() {
        isHit = true;
        ship.setHit();
    }*/
}
    /*
    private Board.TileStatus Status;

    Tile(){
        this(Board.TileStatus.NONE);
    }

    private Tile (Board.TileStatus Status){
        setStatus(Status);
    }
    public Board.TileStatus getStatus() {
        return Status;
    }

    void setStatus(Board.TileStatus Status) {
        this.Status = Status;
    }

    @Override
    public String toString() {
        switch (Status) {
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

