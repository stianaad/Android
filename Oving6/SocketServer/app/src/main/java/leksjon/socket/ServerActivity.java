package leksjon.socket;

import android.app.Activity;
import android.os.Bundle;

public class ServerActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new Server().start();//start server
    }
}