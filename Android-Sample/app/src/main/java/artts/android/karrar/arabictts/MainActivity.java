package artts.android.karrar.arabictts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import artts.android.karrar.arabictts.utls.ArabicTTS;

public class MainActivity extends AppCompatActivity {

    private ArabicTTS tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Defining views
        Button read = (Button) findViewById(R.id.read);
        Button clear = (Button) findViewById(R.id.clear);
        final EditText input = (EditText) findViewById(R.id.editText);

        // Creating a new object of the ArabicTTS librrary
        tts = new ArabicTTS();
        // Preparing the language
        tts.prepare(this);


        // On read click
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = input.getText().toString();
                if(ip!=null && !ip.equals("")){
                    // To read the text inserted
                    tts.talk(ip);
                }
            }
        });

        // Clear the EditText
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("");
            }
        });




    }
}
