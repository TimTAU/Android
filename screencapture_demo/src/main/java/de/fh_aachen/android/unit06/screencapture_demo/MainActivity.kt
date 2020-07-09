package de.fh_aachen.android.unit06.screencapture_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction

// adapted from https://developer.android.com/samples/ui.html

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.sample_content_fragment, ScreenCaptureFragment())
                    .commit()
        }
    }
}
