package com.example.oving2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Calculator extends Activity {
    private int maxLimit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        Intent getMaxLimit = getIntent();
        maxLimit = getMaxLimit.getIntExtra("ovreGrense", 0);
        sendIntent();
        // getRandomToast(); oppg.a
    }

    public void getRandomToast() {
        int random = new Random().nextInt(maxLimit);
        Log.i("random", String.valueOf(random));
        Toast.makeText(getApplicationContext(), String.valueOf(random), Toast.LENGTH_LONG).show();
    }

    public void sendIntent() {
        int random = new Random().nextInt(maxLimit);
        Intent intent = new Intent();
        intent.putExtra("Random1", String.valueOf(random));
        random = new Random().nextInt(maxLimit);
        intent.putExtra("Random2", String.valueOf(random)); // oppg.c sender med tilfeldig tall
        intent.putExtra("maxLimit", maxLimit); // Oppg.b sender med øvre grense
        setResult(RESULT_OK, intent);
        finish(); // oppg.d
    }
}
