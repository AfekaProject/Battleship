package afeka.battleship;


public class TileView extends {

    enum status {Hit, Miss, None , Placed}

    private status currentStatus;

    public TileView(status currentStatus) {
        this.currentStatus = currentStatus;
    }



    public status getStatus() {
        return currentStatus;
    }

    public void setStatus(status status) {
        this.currentStatus = status;
    }





}
