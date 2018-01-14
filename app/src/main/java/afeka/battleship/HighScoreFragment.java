package afeka.battleship;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import afeka.battleship.Model.Score;
import afeka.battleship.View.ScoreAdapter;

public class HighScoreFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private ListView scoreList;
    private ScoreAdapter scoreAdapter;
    private ArrayList<Score> scoreData ;

    public HighScoreFragment() {
        // Required empty public constructor
    }

    public static HighScoreFragment newInstance(String param1, String param2) {
        HighScoreFragment fragment = new HighScoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        scoreList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.getFocusables(position);
                view.setSelected(true);
                if(scoreData!=null)
                    onButtonPressed(position);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        scoreData=new ArrayList<>();
        View view =inflater.inflate(R.layout.fragment_high_score, container, false);
        scoreList = view.findViewById(R.id.scoreList);

        scoreAdapter = new ScoreAdapter(getContext(),scoreData);
        scoreList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        scoreList.setAdapter(scoreAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.onClickRow(position);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onClickRow(int position);
    }


    public Score[] test(){
        Score[] arr = new Score[10];
        for (int i = 0 ; i<arr.length ; i++){
            arr[i] = new Score("id"+i,1,100);
        }
        return arr;
    }

    public void showTable(ArrayList<Score>scores){
        scoreData.clear();

        if(scores.size()>0)
        scoreData.addAll(0,scores);
        scoreAdapter.notifyDataSetChanged();
    }

    public void focusLine(int line){
        scoreList.requestFocusFromTouch();
        scoreList.setSelection(line);
        scoreList.performItemClick(scoreList.getAdapter().getView(line,null,null),
                line, line);

    }


}
