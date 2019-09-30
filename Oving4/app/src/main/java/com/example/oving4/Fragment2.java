package com.example.oving4;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

public class Fragment2 extends Fragment {
    private String[] beskrivelse;
    private TextView mTextView;
    private static int nrValgt =0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment2, container, false);
    }
    public static int getValgtNr() {
        return nrValgt;
    }

    public void youveGotMail(int nr) {
        nrValgt = nr;
        mTextView = (TextView) getView().findViewById(R.id.textView);
        TypedArray bildeBeskrivelse = getResources().obtainTypedArray(R.array.beskrivelse);
        String text = bildeBeskrivelse.getString(nr);

        ImageView bildeView = (ImageView) getView().findViewById( R.id.bilde);
        TypedArray bilder = getResources().obtainTypedArray(R.array.bilder);
        Drawable bilde = bilder.getDrawable(nr);
        bildeView.setImageDrawable(bilde);
        mTextView.setText(text);
        Log.i("Message", text);
    }
}
