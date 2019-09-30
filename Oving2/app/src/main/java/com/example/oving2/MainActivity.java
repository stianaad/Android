package com.example.oving2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity{
    private final int maxLimit = 100;
    private int request_Code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getRandomToast(); // oppg.a
        //TextView tv = (TextView) findViewById(R.id.textView1);
        //tv.setOnTouchListener(this);
    }

    public void onClick(View v) {
        Intent intent = new Intent(this, Calculator.class);
        startActivityForResult(intent, request_Code);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == request_Code && resultCode==RESULT_OK) {
            String tall = data.getStringExtra("Random1");
            Toast.makeText(getApplicationContext(), tall, Toast.LENGTH_LONG).show();
        }
    }

    public void getRandomToast() {
        int random = new Random().nextInt(maxLimit);
        Log.i("random", String.valueOf(random));
        Toast.makeText(getApplicationContext(), String.valueOf(random), Toast.LENGTH_LONG).show();
    }
}
