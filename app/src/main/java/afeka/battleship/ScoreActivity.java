package afeka.battleship;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

import afeka.battleship.Model.Score;
import afeka.battleship.logic.Game;

public class ScoreActivity extends FragmentActivity implements OnMapReadyCallback, HighScoreFragment.OnFragmentInteractionListener,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Score[] scoreList;
    private Marker[] markers;
    private int difficult;
    private HighScoreFragment highScoreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        highScoreFragment = (HighScoreFragment) getSupportFragmentManager().findFragmentById(R.id.scoreTableFragment);
       markers = new Marker[10];
        scoreList = test();



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
        Log.e("marker tag",marker.getTag().toString());
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public void showScoreList(View view) {
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
        scoreList=test();
        difficultButtomMark();
        //updateListView();
        updateMarkersOnMap();

    }

    private void updateListView() {

    }

    private void updateMarkersOnMap (){
        if (mMap!=null){
            mMap.clear();
            for (int i= 0 ; i< scoreList.length ; i++){
                String name = scoreList[i].getName();
                Location location = scoreList[i].getLocation();
                LatLng convertLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                markers[i] = mMap.addMarker(new MarkerOptions().title(name).position(convertLatLng));
                markers[i].setTag((Integer)i);
            }
        }


    }


    public Score[] test(){
        Score[] arr = new Score[10];
        for (int i = 0 ; i<arr.length ; i++){
            Random r = new Random();
            Location l = new Location("dummy");
            l.setLatitude(r.nextDouble()*30);
            l.setLongitude(r.nextDouble()*30);
            arr[i] = new Score("id"+i,1,100,l);
        }
        return arr;
    }

    private void difficultButtomMark(){
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

}
