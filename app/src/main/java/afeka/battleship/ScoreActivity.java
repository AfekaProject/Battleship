package afeka.battleship;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;
import afeka.battleship.Model.Score;

public class ScoreActivity extends FragmentActivity implements OnMapReadyCallback, HighScoreFragment.OnFragmentInteractionListener,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ArrayList<Score>[] scoreList;
    private Marker[] markers;
    private int difficult=1;
    private HighScoreFragment highScoreFragment;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        database = new Database(this);

        markers = new Marker[10];
        scoreList = new ArrayList[3];
        initTables();

        highScoreFragment = (HighScoreFragment) getSupportFragmentManager().findFragmentById(R.id.tableFragment);
        if(highScoreFragment!=null)
            highScoreFragment.showTable(scoreList[difficult-1]);
    }

    private void initTables(){
        for(int i =0 ;i<scoreList.length; i++){
            scoreList[i]=database.getScoreList(i+1);
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        findViewById(R.id.scoreEasy).performClick();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(highScoreFragment!=null)
            highScoreFragment.focusLine((Integer)marker.getTag());
        Log.e("marker tag",marker.getTag().toString());

        return false;
    }

    public void showScoreList(View view) { //when difficulty was chosen
        switch (view.getId()){
            case R.id.scoreEasy:
                this.difficult = 3;
                break;
            case R.id.scoreMedium:
                this.difficult = 2;
                break;
            case R.id.scoreHard:
                this.difficult = 1;
                break;
            default:
                this.difficult = 1;
                break;
        }

        difficultButtonMark();
        if(highScoreFragment!=null)
            highScoreFragment.showTable(scoreList[difficult-1]);
        updateMarkersOnMap();

    }

    private void updateMarkersOnMap (){
        if (mMap!=null){
            mMap.clear();
            for (int i= 0 ; i< scoreList[difficult-1].size() ; i++){
                String name = scoreList[difficult-1].get(i).getName();
                LatLng location = scoreList[difficult-1].get(i).getLocation();
                markers[i] = mMap.addMarker(new MarkerOptions().title(name).position(location));
                markers[i].setTag(i);
            }
        }
    }





    private void difficultButtonMark(){
        int darkBlue = getResources().getColor(R.color.darkBlue);
        int lightBlue = getResources().getColor(R.color.blue);
        switch(difficult){
            case 3:     //Easy
               findViewById(R.id.scoreEasy).setBackgroundColor(darkBlue);
               findViewById(R.id.scoreMedium).setBackgroundColor(lightBlue);
               findViewById(R.id.scoreHard).setBackgroundColor(lightBlue);
                break;
            case 2:     //Medium
                findViewById(R.id.scoreEasy).setBackgroundColor(lightBlue);
                findViewById(R.id.scoreMedium).setBackgroundColor(darkBlue);
                findViewById(R.id.scoreHard).setBackgroundColor(lightBlue);
                break;
            case 1:     //Hard
                findViewById(R.id.scoreEasy).setBackgroundColor(lightBlue);
                findViewById(R.id.scoreMedium).setBackgroundColor(lightBlue);
                findViewById(R.id.scoreHard).setBackgroundColor(darkBlue);
                break;
        }
    }

    @Override
    public void onClickRow(Score score) { //when clicked on a row in the table

    }
}
