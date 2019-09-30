package com.example.oving3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> navn;
    private ArrayList<String> fodselsDato;
    private int request_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navn = new ArrayList<String>();
        fodselsDato = new ArrayList<String>();
        //initierList();
    }

    private void initierList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, navn);
        final ListView listView = (ListView)(findViewById(R.id.listView1));
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View valgt, int posisjon, long id) {
                //       TextView tv = (TextView) findViewById(R.id.beskrivelse);
                //       tv.setText(dyrebeskrivelse[posisjon]);
                //Spinner spinner = (Spinner) findViewById(R.id.spinnerDyr);
                //spinner.setSelection(posisjon);
                Intent sendPerson = new Intent("com.example.oving3.visPerson");
                sendPerson.putExtra("Navn", navn.get(posisjon));
                sendPerson.putExtra("Fodselsdato", fodselsDato.get(posisjon));
                sendPerson.putExtra("Posisjon", posisjon);
                startActivityForResult(sendPerson, request_code);
                //finish();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == request_code && resultCode==RESULT_OK) {
            int posisjon = data.getIntExtra("Posisjon",0);
            navn.set(posisjon, data.getStringExtra("Navn"));
            fodselsDato.set(posisjon, data.getStringExtra("Fodselsdato"));
            initierList();
        }
    }

    public void onClick1(View v) {
        EditText editTextNavn = findViewById(R.id.navn);
        String navn = editTextNavn.getText().toString();
        this.navn.add(navn);

        EditText editTextFodselsdato = findViewById(R.id.fodselsdato);
        String fodselsdato = editTextFodselsdato.getText().toString();
        this.fodselsDato.add(fodselsdato);
        initierList();
        editTextNavn.setText(null);
        editTextFodselsdato.setText(null);
    }
}
