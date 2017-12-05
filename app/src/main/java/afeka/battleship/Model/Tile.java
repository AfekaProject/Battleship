package afeka.battleship.Model;

public class Tile {

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
    }
}
