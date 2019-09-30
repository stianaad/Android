package com.example.oving7;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private DatabaseManager db;
    private boolean clickedTittel = false;
    private boolean clickedForfatter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<String> list = readFile(R.raw.textfile);
        for(int i=0;i<list.size(); i++) {
            Log.i("line", list.get(i));
        }
        try {
            db = new DatabaseManager(this);
            db.clean();
            for(int i=0;i<list.size(); i+=2) {
                db.insert(list.get(i), list.get(i+1));
            }
            //   ArrayList<String> res = db.getAllAuthors();
            ArrayList<String> res = db.getAllBooks();
            //   ArrayList<String> res = db.getBooksByAuthor("Mildrid Ljosland");
            //   ArrayList<String> res = db.getAuthorsByBook("Programmering i C++");
            //   ArrayList<String> res = db.getAllBooksAndAuthors();
            //showResults(res);
        }
        catch (Exception e) {
            //String tekst = e.getMessage();
            //TextView t = (TextView)findViewById(R.id.tw1);
            //t.setText(tekst);
        }
        /*SharedPreferences.Editor editor = getSharedPreferences("preferences", MODE_PRIVATE).edit();
        editor.putString("name", "Elena");
        editor.putInt("idName", 12);
        editor.apply();*/

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        /*boolean allowMultipleUsers = sharedPref.getBoolean(SettingsActivity.ALLOW_MULTIPLE_USERS, false);
        String envId = sharedPref.getString(SettingsActivity.ENVIRONMENT_ID, "");
        String account = sharedPref.getString(SettingsActivity.ACCOUNT, "");*/
        String color = sharedPref.getString("color_preference", "");
        Log.i("COLOR", color);
        ListView lv = (ListView) findViewById(R.id.listTittel);
        ListView lv2 = (ListView) findViewById(R.id.listView);
        if(color.equals("Blue")) {
            lv.setBackgroundColor(Color.BLUE);
            lv2.setBackgroundColor(Color.BLUE);
        } else if(color.equals("White")){
            lv.setBackgroundColor(Color.WHITE);
            lv2.setBackgroundColor(Color.WHITE);
        } else {
            lv.setBackgroundColor(Color.YELLOW);
            lv2.setBackgroundColor(Color.YELLOW);
        }
    }

    public void finnAlleForfattere(View v) {
        ArrayList<String> res;
        if(clickedForfatter) {
            res = new ArrayList<>();
        } else {
            res = db.getAllAuthors();
        }
        clickedForfatter = !clickedForfatter;
        ListView listView=(ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, res);
        listView.setAdapter(adapter);
    }

    public void finnAlleTitler(View v) {

        ArrayList<String> res;
        if(clickedTittel) {
            res = new ArrayList<>();
        } else {
            res = db.getAllBooks();
        }
        clickedTittel = !clickedTittel;
        ListView listView=(ListView)findViewById(R.id.listTittel);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, res);
        listView.setAdapter(adapter);
    }

    private ArrayList<String> readFile(int id) {
        ArrayList<String> sb = new ArrayList<>();
        try {
            InputStream is = getResources().openRawResource(id);
            BufferedReader reader= new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while (line != null) {
                Log.i("LINE", line);
                String[] split = line.split(",");
                sb.add(split[0]); //legger først til forfatternavn
                sb.add(split[1]); //legger til tittel
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,
                    SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

