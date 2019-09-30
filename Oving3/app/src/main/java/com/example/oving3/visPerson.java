package com.example.oving3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class visPerson extends Activity {
    private int posisjon = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vis_person);

        Intent i = getIntent();
        String navn= i.getStringExtra("Navn");
        String fodselsdato= i.getStringExtra("Fodselsdato");
        posisjon = i.getIntExtra("Posisjon", 0);
        EditText editTextNavn = (EditText) findViewById(R.id.navn);
        EditText editTextFodselsdato = (EditText) findViewById(R.id.fodselsdato);
        editTextNavn.setText(navn);
        editTextFodselsdato.setText(fodselsdato);
    }

    public void onClick1(View v) {
        EditText editTextNavn = findViewById(R.id.navn);
        String navn = editTextNavn.getText().toString();

        EditText editTextFodselsdato = findViewById(R.id.fodselsdato);
        String fodselsdato = editTextFodselsdato.getText().toString();

        Intent sendResponse = new Intent();
        sendResponse.putExtra("Navn", navn);
        sendResponse.putExtra("Fodselsdato", fodselsdato);
        sendResponse.putExtra("Posisjon", posisjon);
        setResult(RESULT_OK, sendResponse);
        finish();
    }

    public void onClick2(View view) {
        visPerson.this.finish();
    }
}
