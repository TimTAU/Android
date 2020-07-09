package de.fh_aachen.android.unit02.expandable_demo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

// automatically translated! mixed Kotlin and Java

class MainActivity : AppCompatActivity() {
    var elAdapter: ExpandableListAdapter? = null
    var elView: ExpandableListView? = null
    var dataHeader: ArrayList<String>? = null
    var dataContent: HashMap<String, List<String>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fillData()

        elAdapter = ExpandableListAdapter(this, dataHeader, dataContent)
        elView = findViewById<View>(R.id.lvExp) as ExpandableListView
        elView!!.setAdapter(elAdapter)

    }

    fun clickMe(v: View) {
        val btn = v as Button
        val s = btn.tag as String
        Toast.makeText(this, "click $s", Toast.LENGTH_SHORT).show()
    }

    fun addSomething(v: View?) { // simple example
        val pos = dataHeader!!.size
        dataHeader!!.add("Gruppe new")
        val G: MutableList<String> = ArrayList()
        G.add("Punkt neu")
        dataContent!![dataHeader!![pos]] = G
        elAdapter!!.notifyDataSetChanged()
    }

    // internal data only
    private fun fillData() {
        dataHeader = ArrayList()
        dataContent = HashMap()
        dataHeader!!.add("Gruppe 1")
        dataHeader!!.add("Gruppe 2")
        dataHeader!!.add("Gruppe 3")
        val G1: MutableList<String> = ArrayList()
        G1.add("Punkt 1.1")
        val G2: MutableList<String> = ArrayList()
        G2.add("Punkt 2.1")
        G2.add("Punkt 2.2")
        val G3: MutableList<String> = ArrayList()
        G3.add("Punkt 3.1")
        G3.add("Punkt 3.2")
        G3.add("Punkt 3.3")
        dataContent!![dataHeader!!.get(0)] = G1
        dataContent!![dataHeader!!.get(1)] = G2
        dataContent!![dataHeader!!.get(2)] = G3
    }

}
