package de.fh_aachen.android.unit06.navigationdrawer_demo

import android.os.Bundle
import android.view.Gravity
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toast_layout.*

// old class: android.support.v4.widget.DrawerLayout

class MainActivity : AppCompatActivity() {

    private lateinit var osTitles : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        osTitles = resources.getStringArray(R.array.operating_systems)

        ArrayAdapter(this, R.layout.drawer_list_item, osTitles).apply {
            left_drawer.adapter = this
            left_drawer.onItemClickListener = OnItemClickListener { _, _, position, _ ->
                buildToast(position, osTitles[position]).show()
            }
        }
    }

    private fun buildToast(position:Int, item:String)= Toast(applicationContext).apply {
            setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            duration = Toast.LENGTH_SHORT
            view = layoutInflater.inflate(R.layout.toast_layout, toast_layout_root)
                    .apply {
                        findViewById<TextView>(R.id.textViewToast).text = "pos ${position + 1}: '$item'"
                    }
        }

}


