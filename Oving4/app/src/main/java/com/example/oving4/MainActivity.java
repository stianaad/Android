package com.example.oving4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements Fragment1.OnFragment1Listener{
    Fragment2 fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showImage(false);
    }

    @Override
    public void messageFromFragment1(int nr) {
        fragment2 = (Fragment2) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        fragment2.youveGotMail(nr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void showImage(boolean show) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fmt = fm.beginTransaction();
        Fragment2 fragment = (Fragment2)fm.findFragmentById(R.id.fragment2);
        if(show)fmt.show(fragment);
        else fmt.hide(fragment);
        fmt.addToBackStack(null);
        fmt.commit();
    }

    public void visNesteEllerForrigeBilde(int nr) {
        int nrSendt = Fragment2.getValgtNr();
        fragment2 = (Fragment2) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        Resources res= getResources();
        int bildeNavn = res.getStringArray(R.array.bildenavn).length;
        int sendNr = nr+ nrSendt;
        if((bildeNavn-1)< sendNr) {
            sendNr = 0;
        } else if( sendNr < 0) {
            sendNr = bildeNavn-1;
        }
        fragment2.youveGotMail(sendNr);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.next:
                visNesteEllerForrigeBilde(1);
                return true;
            case R.id.back:
                visNesteEllerForrigeBilde(-1);
                return true;
            case android.R.id.home:
        }
        return super.onOptionsItemSelected(item);
    }
}
