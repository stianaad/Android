package com.example.oving22;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private int request_Code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick1(View v) {
        TextView textViewTall1 = findViewById(R.id.textView1);
        int tall1 = Integer.parseInt(textViewTall1.getText().toString());

        TextView textViewTall2 = findViewById(R.id.textView2);
        int tall2 = Integer.parseInt(textViewTall2.getText().toString());
        //Log.i("tall1",tall1);
        //Log.i("tall2",tall2);

        EditText svar = findViewById(R.id.editText1);

        String svarString = svar.getText().toString();
        if (svarString.matches("")) {
            Toast.makeText(this, "Du må skrive inn et svar!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            int svarInt = Integer.parseInt(svarString);
            if((tall1+tall2) == svarInt) {
                String riktig = getString(R.string.riktig);
                Toast.makeText(getApplicationContext(), riktig, Toast.LENGTH_LONG).show();
            } else {
                String feil = getString(R.string.feil);
                Toast.makeText(getApplicationContext(), feil + " " + (tall1+tall2), Toast.LENGTH_LONG).show();
            }

            EditText ovreGrense = findViewById(R.id.editText2);
            int ovreGrenseInt = Integer.parseInt(ovreGrense.getText().toString());

            Intent intent = new Intent("com.example.oving2.Calculator");
            intent.setAction("android.intent.action.CALC");
            intent.putExtra("ovreGrense", ovreGrenseInt);
            startActivityForResult(intent, request_Code);
            svar.setText(null);
            ovreGrense.setText(String.valueOf(ovreGrenseInt));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == request_Code && resultCode==RESULT_OK) {

            TextView textViewTall1 = findViewById(R.id.textView1);
            textViewTall1.setText(data.getStringExtra("Random1"));

            TextView textViewTall2 = findViewById(R.id.textView2);
            textViewTall2.setText(data.getStringExtra("Random2"));
        }
    }

    public void onClick2(View v) {
        TextView textViewTall1 = findViewById(R.id.textView1);
        int tall1 = Integer.parseInt(textViewTall1.getText().toString());

        TextView textViewTall2 = findViewById(R.id.textView2);
        int tall2 = Integer.parseInt(textViewTall2.getText().toString());

        EditText svar = findViewById(R.id.editText1);
        String svarString = svar.getText().toString();
        if (svarString.matches("")) {
            Toast.makeText(this, "Du må skrive inn et svar!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            int svarInt = Integer.parseInt(svarString);
            if ((tall1 * tall2) == svarInt) {
                String riktig = getString(R.string.riktig);
                Toast.makeText(getApplicationContext(), riktig, Toast.LENGTH_LONG).show();
            } else {
                String feil = getString(R.string.feil);
                Toast.makeText(getApplicationContext(), feil + " " + (tall1 * tall2), Toast.LENGTH_LONG).show();
            }
            EditText ovreGrense = findViewById(R.id.editText2);
            int ovreGrenseInt = Integer.parseInt(ovreGrense.getText().toString());

            Intent intent = new Intent("com.example.oving2.Calculator");
            intent.setAction("android.intent.action.CALC");
            intent.putExtra("ovreGrense", ovreGrenseInt);
            startActivityForResult(intent, request_Code);
            svar.setText(null);
            ovreGrense.setText(String.valueOf(ovreGrenseInt));
        }
    }

}
