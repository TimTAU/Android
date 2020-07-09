package de.fh_aachen.android.unit01.resource_demo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this::onClickButton);

        // button.setText(getResources().getString(R.string.button_text));
        // button.setBackgroundResource(R.color.colorButton);
        // button.setBackgroundColor(getResources().getColor(R.color.colorButton,null));
    }
    public void onClickButton(View v) {
        String toastText = getResources().getString(R.string.toast_text);
        Toast.makeText(this,toastText,Toast.LENGTH_LONG).show();
    }

}
