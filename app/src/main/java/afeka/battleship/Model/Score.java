package afeka.battleship.Model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;

public class Score {

    private String name;
    private int difficult;
    private int score;
    private Date date;
    private LatLng location;

    public Score(String name, int difficult, int score, Date date, Location location){
        setName(name);
        this.difficult = difficult;
        this.score = score;
        this.date = date;
        this.location = new LatLng(location.getLatitude(),location.getLongitude());
    }

    public Score(String name, int difficult, int score, Date date, LatLng location){
        setName(name);
        this.difficult = difficult;
        this.score = score;
        this.date = date;
        this.location = location;
    }

    public Score (String name ,int difficult, int score,Location location){
        this(name, difficult, score , Calendar.getInstance().getTime(),location);
    }

    public Score(String name, int difficult, int score) {
        setName(name);
        this.difficult = difficult;
        this.score = score;
        this.date = Calendar.getInstance().getTime();
    }


    public void setName(String name) {
        if(name.length()>10)
           name = name.substring(0,9);

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getDifficult() {
        return difficult;
    }

    public int getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }

    public LatLng getLocation(){
        return location;
    }


}
