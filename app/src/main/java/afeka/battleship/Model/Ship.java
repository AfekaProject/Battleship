package afeka.battleship.Model;

public class Ship {

    private int size;
    private int hitsCounter;
    private int id;

    public int getSize() {
        return size;
    }

    public Ship(int size){
        this.size = size;
        hitsCounter = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHitsCounter() {
        return hitsCounter;
    }

    public boolean isDrowned() {
        if (hitsCounter >= size)
            return true;
        else
            return false;
    }

    public boolean setHit(){
        hitsCounter++;
        return isDrowned();


    }

}
