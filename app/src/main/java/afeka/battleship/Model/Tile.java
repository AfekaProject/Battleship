package afeka.battleship.Model;

public class Tile {
    public enum Status {NONE, HIT, MISS, PLACED, DROWNED}

    private int x;
    private int y;
    private Ship ship;

    private Status status;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        status = status.NONE;

    }

    @Override
    public String toString() {
        switch (status) {
            case NONE:
                return "";
            case PLACED:
                return ("S" + ship.getId());
            case HIT:
                return "X";
            case MISS:
                return "~";
            case DROWNED:
                return "D";
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
        if (status.equals(status.HIT))
            return true;
        else
            return false;
    }

    public boolean setHit() {
        status = status.HIT;
        return ship.setHit(); //update ship's hit counter
    }

    public void setMiss() {
        status = status.MISS;
    }

    public void setPlaced(Ship ship) {
        setShip(ship);
        status = status.PLACED;
    }

    public void setDrowned() {
        status = Status.DROWNED;
    }

    public boolean isFreeToClick() {
        return (!status.equals(Status.HIT)  && !status.equals(Status.MISS));
    }

    public boolean isPlaced() {
        return status.equals(Status.PLACED);
    }
}

