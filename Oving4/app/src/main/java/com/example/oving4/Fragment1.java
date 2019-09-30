package com.example.oving4;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

public class Fragment1 extends ListFragment {
    private String[] bildeNavn;
    private String[] answers;
    private int clicked = -1;
    private OnFragment1Listener mCallback;

    public Fragment1() {
    }

    public interface OnFragment1Listener {
        void messageFromFragment1(int nr);
    }

    // This method insures that the Activity has actually implemented our
    // listener and that it isn't null.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragment1Listener) {
            mCallback = (OnFragment1Listener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnGreenFragmentListener");
        }
    }

    public void sendText(int nr) {
        mCallback.messageFromFragment1(nr);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res= getResources();
        bildeNavn = res.getStringArray(R.array.bildenavn);
        //answers = res.getStringArray(R.array.answers);
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, bildeNavn));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // ListView can only be accessed here, not in onCreate()
        super.onViewCreated(view, savedInstanceState);
        //    return inflater.inflate(R.layout.fragment1, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        showImage(true);
        sendText(position);
        clicked = position;
    }

    public void showImage(boolean show) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fmt = fm.beginTransaction();
        Fragment2 fragment = (Fragment2)fm.findFragmentById(R.id.fragment2);
        if(show)fmt.show(fragment);
        else fmt.hide(fragment);
        fmt.addToBackStack(null);
        fmt.commit();
    }


}
