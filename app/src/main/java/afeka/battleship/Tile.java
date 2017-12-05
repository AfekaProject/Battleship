package afeka.battleship;

public class Tile {

    private Board.tileStatus status;

    public Tile (){
        setStatus(Board.tileStatus.None);
    }

    public Tile (Board.tileStatus status){
        setStatus(status);
    }
    public Board.tileStatus getStatus() {
        return status;
    }

    public void setStatus(Board.tileStatus status) {
        this.status = status;
    }
}
