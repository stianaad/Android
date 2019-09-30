package com.example.oving5;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class FragmentName  extends Fragment {
    private OnFragmentNameListener mCallback;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmentname, container, false);
    }

    public interface OnFragmentNameListener {
        void messageFromFragmentName(String name, String kortnummer);
    }

    public void sendInformation() {
        EditText editTextName = (EditText) getView().findViewById(R.id.navn);
        EditText editTextKort = (EditText) getView().findViewById(R.id.kort);
        mCallback.messageFromFragmentName(editTextName.getText().toString(), editTextKort.getText().toString());
        editTextName.setText(null);
        editTextKort.setText(null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentNameListener) {
            mCallback = (OnFragmentNameListener) activity;
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

}
