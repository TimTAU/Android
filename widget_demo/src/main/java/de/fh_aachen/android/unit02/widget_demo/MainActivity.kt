package de.fh_aachen.android.unit02.widget_demo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText7.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length <= progressBar1.max) progressBar1.progress = s.length
            }
        })

        radioGroup.setOnCheckedChangeListener { _: RadioGroup?, checkedId: Int ->
            findViewById<RadioButton>(checkedId).let {
                Toast.makeText(this, "new checked: ${it.text}", Toast.LENGTH_SHORT).show()
            }
        }

        frameLayout1.setOnLongClickListener { view: View? ->
            click1(view)
            true
        }

    }

    fun click1(view: View?) {
        checkBox1.isChecked = !checkBox1.isChecked
    }

    fun click2(view: View?) {
        checkBox2.isChecked = !checkBox2.isChecked
    }

    fun check1(view: View?) {
        checkBox2.isChecked = checkBox1.isChecked
    }

    fun check2(view: View?) {
        checkBox1.isChecked = checkBox2.isChecked
    }

    fun switch1(view: View?) {
        val sw = findViewById<Switch>(R.id.switch1)
        val tb = findViewById<ToggleButton>(R.id.toggleButton1)
        toggleButton1.isChecked = switch1.isChecked
    }

    fun toggle1(view: View?) {
        switch1.isChecked = toggleButton1.isChecked
    }

}
// 64-11 = 53

/*

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editText7);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = s.length();
                ProgressBar pb = findViewById(R.id.progressBar1);
                if (len<=pb.getMax())
                    pb.setProgress(len);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        RadioGroup rg = findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener((RadioGroup group, int checkedId)-> {
            RadioButton rb = findViewById(checkedId);
                Toast.makeText(this,"new checked: "+rb.getText(),Toast.LENGTH_SHORT).show();
        });


        FrameLayout fl1 = findViewById(R.id.frameLayout1);
        fl1.setOnLongClickListener(view -> {
            click1(view);
            return true;
        });
    }

    public void click1(View view) {
        CheckBox cb = findViewById(R.id.checkBox1);
        cb.setChecked(!cb.isChecked());
    }

    public void click2(View view) {
        CheckBox cb = findViewById(R.id.checkBox2);
        cb.setChecked(!cb.isChecked());
    }

    public void check1(View view) {
        CheckBox cb1 = findViewById(R.id.checkBox1);
        CheckBox cb2 = findViewById(R.id.checkBox2);
        cb2.setChecked(cb1.isChecked());
    }

    public void check2(View view) {
        CheckBox cb1 = findViewById(R.id.checkBox1);
        CheckBox cb2 = findViewById(R.id.checkBox2);
        cb1.setChecked(cb2.isChecked());
    }

    public void switch1(View view) {
        Switch sw = findViewById(R.id.switch1);
        ToggleButton tb = findViewById(R.id.toggleButton1);
        tb.setChecked(sw.isChecked());
    }

    public void toggle1(View view) {
        Switch sw = findViewById(R.id.switch1);
        ToggleButton tb = findViewById(R.id.toggleButton1);
        sw.setChecked(tb.isChecked());
    }

}
// 141-69 = 72

 */