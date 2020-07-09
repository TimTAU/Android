package de.fh_aachen.android.unit00.hello_click;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(
                this::onClickButton
        );

//        findViewById(R.id.button).setOnClickListener(
//                (v)->onClickButton(v)
//        );
//
//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickButton(v);
//            }
//        });

    }

    private static final String TAG = "Hello_Click";

    public void onClickButton(View v) {
        Toast.makeText(this,"Pressed!",Toast.LENGTH_LONG).show();
        Log.i(TAG,"onClickButton - button pressed!");
    }

}
