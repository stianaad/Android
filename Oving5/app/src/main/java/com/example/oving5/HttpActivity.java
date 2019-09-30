package com.example.oving5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class HttpActivity extends AppCompatActivity implements FragmentName.OnFragmentNameListener, FragmentVelgTall.OnFragmentVelgTallListener{
    private HttpWrapperThreaded network;
    private boolean visNameFragment = true;
    private boolean visVelgTallFragment = false;
    final static String TAG = "HttpActivity";
    final static String urlToServer = "http://tomcat.stud.iie.ntnu.no/studtomas/tallspill.jsp";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w(TAG,"Contacting server...");
        network = new HttpWrapperThreaded(this, urlToServer);
        visVelgTall(visVelgTallFragment);
    }

    public void onClickSendName(View v) {
        if(visNameFragment) {
            FragmentName fragment2 = (FragmentName) getSupportFragmentManager().findFragmentById(R.id.fragmentName);
            fragment2.sendInformation();
        } else {
            FragmentVelgTall fragment2 = (FragmentVelgTall) getSupportFragmentManager().findFragmentById(R.id.fragmentVelgTall);
            fragment2.sendInformation();
        }
    }

    public void onClickStartNytt (View v) {
        visNameFragment = true;
        visVelgTallFragment = false;
        visName(visNameFragment);
        visVelgTall(visVelgTallFragment);
    }

    public void messageFromFragmentName(String name, String kortnummer) {
        Log.i("name", name);
        Log.i("kortnummer", kortnummer);
        network.runHttpRequestInThread(HttpWrapperThreaded.HttpRequestType.HTTP_GET, createRequestValues(true, name, kortnummer, ""));
    }

    public void messageFromFragmentVelgTall(int tall) {
        Log.i("Svar på main", String.valueOf(tall));
        network.runHttpRequestInThread(HttpWrapperThreaded.HttpRequestType.HTTP_GET, createRequestValues(false, "", "", String.valueOf(tall)));
    }

    //Method for showing response from HTTP server
    public void showResponse(String response){
        Log.w(TAG,"Server responded with: " + response);
        //Toast.makeText(getBaseContext(), "Look in log for response!!!", Toast.LENGTH_LONG).show();
        FragmentVelgTall fragment2 = (FragmentVelgTall) getSupportFragmentManager().findFragmentById(R.id.fragmentVelgTall);
        visNameFragment = false;
        visVelgTallFragment = true;
        visName(visNameFragment);
        visVelgTall(visVelgTallFragment);
        fragment2.setText(response);
    }

    /* Lets make some parameters for the HTTP request */
    private Map<String,String> createRequestValues(boolean navn, String name, String kortnummer, String tall){
        Map<String,String> valueList = new HashMap<String,String>();
        if(navn) {
            valueList.put("navn", name);
            valueList.put("kortnummer", kortnummer);
        } else {
            valueList.put("tall", tall);
        }
        return valueList;
    }

    public boolean onCreateOptionsMenu(Menu meny){
        super.onCreateOptionsMenu(meny);//kaller metoden som vi arver, er dog ikke nødvendig
        Log.w("onCreateOptionsMenu()","meny laget"); //skriver ut til logg, vises i LogCat
        return true; //true her gjør at menyen vil vises
    }

    public void visVelgTall(boolean show) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fmt = fm.beginTransaction();
        FragmentVelgTall fragment = (FragmentVelgTall) fm.findFragmentById(R.id.fragmentVelgTall);
        if(show)fmt.show(fragment);
        else fmt.hide(fragment);
        fmt.addToBackStack(null);
        fmt.commit();
    }

    public void visName(boolean show) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fmt = fm.beginTransaction();
        FragmentName fragment = (FragmentName) fm.findFragmentById(R.id.fragmentName);
        if(show)fmt.show(fragment);
        else fmt.hide(fragment);
        fmt.addToBackStack(null);
        fmt.commit();
    }

}
