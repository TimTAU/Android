package de.fh_aachen.android.unit00.hello_kotlin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivityJava extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(
                this::onClickButton
        );
    }

    private static final String TAG = "Hello_Kotlin";

    public void onClickButton(View v) {
        Toast.makeText(this,"Pressed!",Toast.LENGTH_LONG).show();
        Log.i(TAG,"onClickButton - button pressed!");
    }

}
