package afeka.battleship;


public class Tile {

    enum status {Hit, Miss, None , Placed}

    private status currentStatus;

    public Tile(status currentStatus) {
        this.currentStatus = currentStatus;
    }



    public status getStatus() {
        return currentStatus;
    }

    public void setStatus(status status) {
        this.currentStatus = status;
    }





}
