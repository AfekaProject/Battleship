package afeka.battleship;

import android.graphics.Color;
import android.graphics.ColorFilter;
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

import afeka.battleship.Model.Score;
import afeka.battleship.logic.Game;

public class ScoreActivity extends FragmentActivity implements OnMapReadyCallback, HighScoreFragment.OnFragmentInteractionListener,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Score[] scoreList;
    private int difficult;
    private HighScoreFragment highScoreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFargment);
        mapFragment.getMapAsync(this);
        highScoreFragment = (HighScoreFragment) getSupportFragmentManager().findFragmentById(R.id.scoreTableFragment);
        findViewById(R.id.scoreEasy).performClick();



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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-33.852, 151.211);
        MarkerOptions m = new MarkerOptions().position(sydney).title("lala");
        mMap.setOnMarkerClickListener(this);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
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

        difficultButtomMark();
        //updateListView();
        //updateMarkersOnMap();

    }

    private void updateListView() {

    }

    private void updateMarkersOnMap (){
        mMap.clear();
        for (int i= 0 ; i< scoreList.length ; i++){
           // Marker m = new MarkerOptions().
        }
    }

    private void difficultButtomMark(){
        int darkBlue = getResources().getColor(R.color.drakBlue);
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
