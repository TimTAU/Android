package de.fh_aachen.android.unit01.panel_demo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivityJava extends AppCompatActivity {
    int gridSize = 0;
    ArrayList<View> gridButtons = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildGrid(3);
    }

    private void buildGrid(int newSize) {
        gridSize = newSize;
        gridButtons = new ArrayList<>(newSize);

        // das Ziellayout
        ConstraintLayout constraintLayout = findViewById(R.id.rootLayout);
        constraintLayout.removeAllViews();

        // Constraints hier sammeln
        ConstraintSet set = new ConstraintSet();
        set.clone(constraintLayout);

        // obere und untere "Grenze" zwischen 10% und 40%
        int idHG05 = View.generateViewId();
        set.create(idHG05, ConstraintSet.HORIZONTAL_GUIDELINE);
        set.setGuidelinePercent(idHG05, 0.10f);

        int idHG10 = View.generateViewId();
        set.create(idHG10, ConstraintSet.HORIZONTAL_GUIDELINE);
        set.setGuidelinePercent(idHG10, 0.40f);

        // gridSize+1 vertikale Guidelines zwischen 5% und 95%
        float marginL = 0.05f, marginR = 0.05f,
                dx = (1.0f-marginL-marginR) / (float) gridSize;
        ArrayList<Integer> ids = new ArrayList<>(gridSize +1);
        for (int i = 0; i<= gridSize; ++i) {
            int idG = View.generateViewId();
            set.create(idG, ConstraintSet.VERTICAL_GUIDELINE);
            set.setGuidelinePercent(idG, marginL+i*dx);
            ids.add(idG);
        }

        // gridSize Buttons zwischen den Guidelines i und i+1
        for (int i = 0; i< gridSize; ++i) {
            Button button = new Button(this);
            int idB = View.generateViewId();
            button.setId(idB);
            button.setText("Button "+i);
            button.setTag(i);
            button.setOnClickListener(this::onClickButton); // Methodenreferenz
            constraintLayout.addView(button);
            gridButtons.add(button);

            set.connect(idB, ConstraintSet.LEFT, ids.get(i), ConstraintSet.RIGHT, 0);
            set.connect(idB, ConstraintSet.RIGHT, ids.get(i+1), ConstraintSet.LEFT, 0);
            set.connect(idB, ConstraintSet.TOP, idHG05, ConstraintSet.BOTTOM, 0);
            set.connect(idB, ConstraintSet.BOTTOM, idHG10, ConstraintSet.TOP, 0);
        }

        // und alle Constraints aktivieren
        set.applyTo(constraintLayout);
    }

    private void onClickButton(View v) {
        int index = (int)v.getTag();
        Toast.makeText(this,"Button "+index+" pressed!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_size3:
                buildGrid(3);
                break;
            case R.id.action_size4:
                buildGrid(4);
                break;
            case R.id.action_size5:
                buildGrid(5);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
