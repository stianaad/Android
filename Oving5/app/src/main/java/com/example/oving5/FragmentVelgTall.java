package com.example.oving5;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FragmentVelgTall extends Fragment {
    private OnFragmentVelgTallListener mCallback;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmentvelgtall, container, false);
    }

    public interface OnFragmentVelgTallListener {
        void messageFromFragmentVelgTall(int tall);
    }

    public void sendInformation() {
        EditText editText = (EditText) getView().findViewById(R.id.skrivTall);
        //Log.i("respons", editText.getText().toString());
        mCallback.messageFromFragmentVelgTall(Integer.parseInt(editText.getText().toString()));
        editText.setText(null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentVelgTallListener) {
            mCallback = (OnFragmentVelgTallListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnGreenFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public void setText(String text) {
        TextView tx = (TextView) getView().findViewById(R.id.responsTall);
        tx.setText(text);
    }
}
