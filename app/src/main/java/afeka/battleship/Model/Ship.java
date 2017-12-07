package afeka.battleship.Model;

public class Ship {


    private int size;
    private int hitsCounter;

    public int getSize() {
        return size;
    }

    public Ship(int size){
        this.size = size;
        hitsCounter = 0;
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

    public void setHit(){
        hitsCounter++;
    }

}
