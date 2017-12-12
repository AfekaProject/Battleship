package afeka.battleship.Model;

import java.util.ArrayList;

public class Ship {

    private int size;
    private int hitsCounter;
    private int id;
    private ArrayList<Tile> tileList;

    public int getSize() {
        return size;
    }

    public Ship(int size){
        this.size = size;
        hitsCounter = 0;
        tileList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addTile (Tile t){
        tileList.add(t);
    }

    public boolean isDrowned() {
        if (hitsCounter >= size){
            for (int i=0 ; i<tileList.size() ; i++){
                tileList.get(i).setDrowned();
            }
            return true;
        }
        else
            return false;
    }

    public boolean setHit(){
        hitsCounter++;
        return isDrowned();


    }

}