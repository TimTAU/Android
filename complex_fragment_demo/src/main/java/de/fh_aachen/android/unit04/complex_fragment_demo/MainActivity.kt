package de.fh_aachen.android.unit04.complex_fragment_demo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

// fragments are not simple
// https://proandroiddev.com/the-seven-actually-10-cardinal-sins-of-android-development-491d2f64c8e0

class MainActivity : AppCompatActivity() {
    var frag1: Fragment1 = Fragment1()
    var frag2: Fragment2 = Fragment2()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // do not use ctor, use arguments
        frag2.arguments = Bundle().apply { putString("key", "value") }

        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentcontainer, frag1)
                .commit()

        // Problem: Fragment widgets are not accessible here
    }

    fun toggleFragment(v: View?) {
        if (toggleButton1.isChecked) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentcontainer, frag2)
                    .commit()
        } else {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentcontainer, frag1)
                    .commit()
        }
    }

    fun clickNumber(view: View) {
        val btn = view as Button
        val n = btn.text.toString().toInt()
        // use same id... goog idea?
        val tv = findViewById<TextView>(R.id.textViewNumbers)
        val s = tv.text.toString() + n
        tv.text = s
    }

    fun clickC(view: View?) {
        val tv = findViewById<TextView>(R.id.textViewNumbers)
        tv.text = ""
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class Fragment1 : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            Log.v("Fragment1", "create")
            return inflater.inflate(R.layout.fragment1, container, false).apply {
                findViewById<TextView>(R.id.textViewNumbers).text="0"
            }
        }
    }

    class Fragment2 : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            Log.v("Fragment2", "create, val: ${arguments?.getString("key")}")
            return inflater.inflate(R.layout.fragment2, container, false)
        }
    }

}
