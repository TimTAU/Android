package de.fh_aachen.android.unit02.preferences_demo

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.charset.Charset
import java.util.*

const val MY_KEY = "myKey"
const val MY_FILE = "myData"

class MainActivity : AppCompatActivity() {

    enum class Mode { Prefs, File }

    private var mode: Mode = Mode.Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radiogroup1.apply {
            check(R.id.radioButton1)
            setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.radioButton1 -> mode = Mode.Prefs
                    R.id.radioButton2 -> mode = Mode.File
                }
            }
        }
        button1.setOnClickListener { saveData() }
        button2.setOnClickListener { loadData() }
    }

    private fun saveData() {
        when (mode) {
            Mode.Prefs -> {
                getPreferences(Context.MODE_PRIVATE).edit().let {
                    it.putString(MY_KEY, editText1.text.toString())
                    it.apply() // apply=background, commit=immediately
                }
            }
            Mode.File -> {
                try {
                    openFileOutput(MY_FILE, Context.MODE_PRIVATE).use {
                        it.write(editText1.text.toString().toByteArray(Charset.forName("UTF-8")))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun loadData() {
        when (mode) {
            Mode.Prefs -> {
                editText1.setText(getPreferences(Context.MODE_PRIVATE).getString(MY_KEY, "-"))
            }
            Mode.File -> {
                try {
                    openFileInput(MY_FILE).use {
                        editText1.setText(Scanner(it, "UTF-8").useDelimiter("\\A").next())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}
// 74-11 = 63

/*

public class MainActivity extends AppCompatActivity {

    public enum Mode { Prefs, File };

    private Mode mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup rg = findViewById(R.id.radiogroup1);
        rg.check(R.id.radioButton1);
        mode = Mode.Prefs;

        Button btn = findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(null);
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                        mode = Mode.Prefs; break;
                    case R.id.radioButton2:
                        mode = Mode.File; break;
                }
            }
        });

    }

    private static final String myKey = "myKey";
    private static final String myFile = "myData";

    public void saveData(View view) {
        EditText edit = findViewById(R.id.editText1);
        String s = edit.getText().toString();

        switch(mode) {
            case Prefs:
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(myKey, s);
                editor.apply(); // apply=background, commit=immediately
                break;
            case File:
                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(myFile, Context.MODE_PRIVATE);
                    outputStream.write(s.getBytes(Charset.forName("UTF-8")));
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void loadData(View view) {
        EditText edit = findViewById(R.id.editText1);
        String s;

        switch(mode) {
            case Prefs:
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                s = sharedPref.getString(myKey,"-");
                edit.setText(s);
                break;
            case File:
                FileInputStream inputStream;
                try {
                    inputStream = openFileInput(myFile);
                    s = new Scanner(inputStream,"UTF-8").useDelimiter("\\A").next();
                    inputStream.close();
                    edit.setText(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

}
// 166-78 = 88

 */