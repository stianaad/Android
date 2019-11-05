package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.content.res.Resources;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TetrisView.TetrisCallback {

    public static String TAG = "test";
    public static int LEFT = -1;
    public static int RIGHT = 1;
    private TetrisView tetrisView;
    private String mFinish;
    private String mPause;
    private String mRestart;
    private String hjelp;
    private String startNytt;
    private String skiftSprak;
    public static PopupWindow w;
    private static MainActivity mainActivity;
    private String activeLanguage;
    private View sprakView;
    private Menu menuyyyyy;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale locale = getResources().getConfiguration().locale;
        if(!locale.getLanguage().equals("nb")){
            activeLanguage= "en";
            updateLanguage(locale,activeLanguage ,activeLanguage);
            Log.i("Skiftet", skiftSprak);
        } else {
            activeLanguage= "nb";
            updateLanguage(locale,activeLanguage ,"rNo");

        }
        setContentView(R.layout.activity_main);
        tetrisView = (TetrisView) findViewById(R.id.snakeView);
        tetrisView.setGameCallback(this);
    }

    @Override
    public void onEvent(boolean value){
        if(value){
            tetrisView.getThread().setPaused(true);
            //popUpStartNytt();
            onCreateOptionsMenu(menuyyyyy);
        }
    }

    public void updateLanguage(Locale locale, String lang, String country) {
        locale = new Locale(lang,country);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = getBaseContext().getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        updateResoures(resources);
    }

    public void updateResoures(Resources resources) {
        mFinish = resources.getString(R.string.finish);
        mPause=resources.getString(R.string.pause);
        mRestart=resources.getString(R.string.resume);
        hjelp = resources.getString(R.string.hjelp);
        startNytt = resources.getString(R.string.startNytt);
        skiftSprak = resources.getString((R.string.changeLanguage));
        Log.i("Skiftet", resources.getString(R.string.rotate));
        invalidateOptionsMenu();
    }

    public static MainActivity getInstance() {
        return mainActivity;
    }
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add(mFinish);
        menu.add(mPause);
        menu.add(mRestart);
        menu.add(hjelp);
        menu.add(startNytt);
        menu.add(skiftSprak);
        menuyyyyy = menu;
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getTitle().equals(mFinish)){
            tetrisView.getThread().setRunning(false);
            finish();
        }
        else if (item.getTitle().equals(mPause)) {
            tetrisView.getThread().setPaused(true);
            //popUpStartNytt();
        }
        else if (item.getTitle().equals(mRestart)) {
            tetrisView.getThread().setPaused(false);
            tetrisView.setFocusable(true); // make sure we get key events
        } else if(item.getTitle().equals(hjelp)){
            tetrisView.getThread().setPaused(true);
            popUpHjelp(R.layout.popup, getApplicationContext(),(LinearLayout)findViewById(R.id.linearLayout), tetrisView);
        } else if(item.getTitle().equals(startNytt)) {
            tetrisView.startNytt();
        } else if(item.getTitle().equals(skiftSprak)){
            tetrisView.getThread().setPaused(true);
            popupSprak();
        }
        return true;
    }
    protected void onPause() {
        super.onPause();
        tetrisView.getThread().setPaused(true); // pause game when Activity pauses
    }

    public static void popUpHjelp(int layout, Context context, LinearLayout linearLayout, final TetrisView tw) {
        Context mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(layout,null);

        w = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout ll = linearLayout;
        w.showAtLocation(ll, Gravity.CENTER,0,0);
        Button closeButton = (Button) customView.findViewById(R.id.button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                w.dismiss();
                tw.getThread().setPaused(false);
                tw.setFocusable(true);
            }
        });
    }

    public void popupSprak(){
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.sprak,null);
        sprakView = customView;

        w = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout ll = findViewById(R.id.linearLayout);
        w.showAtLocation(ll, Gravity.CENTER,0,0);
        if(activeLanguage.equals("en")){
            RadioButton rd = customView.findViewById(R.id.radioEn);
            rd.setChecked(true);
        } else {
            RadioButton rd = customView.findViewById(R.id.radioNo);
            rd.setChecked(true);
        }
        Button closeButton = (Button) customView.findViewById(R.id.endre);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton rd = sprakView.findViewById(R.id.radioEn);
                Locale locale = getResources().getConfiguration().locale;
                RadioButton rdNo = sprakView.findViewById(R.id.radioNo);
                if(rdNo.isChecked()){
                    activeLanguage = "no";
                    updateLanguage(locale, "nb", "rNo");
                    rd.setChecked(false);
                    rdNo.setChecked(true);

                    Log.i("NB","SKIFTET TIL NB");
                } else {
                    activeLanguage = "en";
                    updateLanguage(locale, "en", "en");
                    rd.setChecked(true);
                    rdNo.setChecked(false);

                    Log.i("NB","SKIFTET TIL GB");
                }
                Resources resources = getBaseContext().getResources();
                tetrisView.updateLanguageText(resources.getString(R.string.rotate), resources.getString(R.string.gameOver));
                w.dismiss();
                tetrisView.getThread().setPaused(false);
                tetrisView.setFocusable(true);
            }
        });
    }

    public  void popUpStartNytt() {
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.nyttspill,null);

        w = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout ll = findViewById(R.id.linearLayout);
        w.showAtLocation(ll, Gravity.CENTER,0,0);
        /*Button closeButton = (Button) customView.findViewById(R.id.button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                w.dismiss();
            }
        });*/
    }

    public void finishedAndStartAgain(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
    }
}
