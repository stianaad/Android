package com.example.oving1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),"Hello Javatpoint", Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu meny){
        super.onCreateOptionsMenu(meny);//kaller metoden som vi arver, er dog ikke nødvendig
        meny.add("Stian"); //legger til meny-valg med teksten «Valg 1»
        meny.add("Ådnanes");
        Log.i("onCreateOptionsMenu()","meny laget"); //skriver ut til logg, vises i LogCat
        return true; //true her gjør at menyen vil vises
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getTitle().equals("Stian")){
            Log.i("onOptionsItemSelected()","Valg 'Stian' er trykket av brukeren");
        } else if (item.getTitle().equals("Ådnanes")) {
            Log.i("onOptionsItemSelected()","Valg 'Ådnanes' er trykket av brukeren");
        }
        return true; //hvorfor true her? Se API-dokumentasjonen!!
    }
}
