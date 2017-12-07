package afeka.battleship.Model;

public class Tile {
    public enum status {NONE,HIT,MISS,PLACED}
    private int x;
    private int y;
    private boolean isHit;
    private Ship ship;

    public Tile(int x , int y){
        this.x = x;
        this.y = y;
        isHit = false;

    }
    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }


    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public void setHitTileInShip() {
        isHit = true;
        ship.setHit();
    }
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
