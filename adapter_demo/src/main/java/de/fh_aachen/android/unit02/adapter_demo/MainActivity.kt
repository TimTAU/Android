package de.fh_aachen.android.unit02.adapter_demo

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // var adapter: ArrayAdapter<String>? = null
    // var entries: ArrayList<String>? = null
    lateinit var adapter: ArrayAdapter<String>
    var entries = mutableListOf<String>()
    var lastNo = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ArrayAdapter(this, android.R.layout.simple_list_item_1, entries).apply {
            adapter = this
            listView1.adapter = this
        }
        addData()
        addData()
        addData(notify = true)

// original translation from Java:
//
//        entries = ArrayList()
//        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, entries!!)
//
//        val listView = findViewById<ListView>(R.id.listView1)
//        listView.adapter = adapter
//
//        addNextNumber()
//        addNextNumber()
//        addNextNumber()
//        adapter!!.notifyDataSetChanged()
    }

    private fun addData(notify:Boolean = false) {
        entries.add("" + ++lastNo)
        if (notify)
            adapter.notifyDataSetChanged()
    }

    private fun delData(notify:Boolean = false) {
        if (entries.size > 0) {
            entries.removeAt(entries.size - 1)
            if (notify)
                adapter.notifyDataSetChanged()
        }
    }

    fun onClickButtonAdd(v: View?) = addData(notify = true)

    fun onClickButtonDel(v: View?) = delData(notify = true)

}
