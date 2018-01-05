package afeka.battleship.Model;

import android.location.Location;
import java.util.Calendar;
import java.util.Date;

public class Score {
    private String name;
    private int difficult;
    private int score;
    private Date date;
    private Location location;

    public Score(String name, int difficult, int score, Date date, Location location){
        this.name = name;
        this.difficult = difficult;
        this.score = score;
        this.date = date;
        this.location = location;
    }

    public Score (String name ,int difficult, int score,Location location){
        this(name, difficult, score , Calendar.getInstance().getTime(),location);
    }

    public Location getLocation(){
        return location;
    }
}