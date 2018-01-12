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

import afeka.battleship.Model.Score;
import afeka.battleship.View.ScoreAdapter;


public class HighScoreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView scoreList;
    private ScoreAdapter scoreAdapter;
    private Score[] scoreData = null;

    public HighScoreFragment() {
        // Required empty public constructor
    }

    public static HighScoreFragment newInstance(String param1, String param2) {
        HighScoreFragment fragment = new HighScoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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
                onButtonPressed(scoreData[position]);

            }

        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_high_score, container, false);
        scoreList = view.findViewById(R.id.scoreList);
        scoreData=test();
        scoreAdapter = new ScoreAdapter(getContext(),scoreData);
        scoreList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        scoreList.setAdapter(scoreAdapter);
        scoreAdapter.notifyDataSetChanged();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Score score) {
        if (mListener != null) {
            mListener.onClickRow(score);
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
        // TODO: Update argument type and name
        void onClickRow(Score score);
    }


    public Score[] test(){
        Score[] arr = new Score[10];
        for (int i = 0 ; i<arr.length ; i++){
            arr[i] = new Score("id"+i,1,100);
        }
        return arr;
    }

    public void showTable(Score[]scores){

       System.arraycopy(scores,0,scoreData,0,scores.length);

       scoreAdapter.notifyDataSetChanged();


    }

}
