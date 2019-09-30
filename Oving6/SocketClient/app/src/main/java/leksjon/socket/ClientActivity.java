package leksjon.socket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ClientActivity extends Activity {
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //new Client().start();
    }

    public void sendTall(View v ) throws InterruptedException{
        EditText tall1 = (EditText) findViewById(R.id.tall1);
        int tall1Int =  Integer.parseInt(tall1.getText().toString());
        EditText tall2 = (EditText) findViewById(R.id.tall2);
        int tall2Int =  Integer.parseInt(tall2.getText().toString());

        Client c = new Client();
        c.setTall(tall1Int, tall2Int);
        c.start();
        c.join();

        Log.i("SVAR på main", c.getSvar());
        TextView tx = (TextView) findViewById(R.id.svar);
        tx.setText("Svaret er "+ c.getSvar());
    }
}