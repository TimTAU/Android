package de.fh_aachen.android.unit01.panel_demo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import java.util.*

class MainActivityKotlin : AppCompatActivity() {

    private var gridSize = 0
    // private var gridButtons: ArrayList<View>? = null
    private var gridButtons: ArrayList<View> = arrayListOf() // empty

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buildGrid(3)
    }

    private fun buildGrid(newSize: Int) {
        gridSize = newSize
        gridButtons = ArrayList(newSize)
        // das Ziellayout
        // val constraintLayout: ConstraintLayout = findViewById<ConstraintLayout>(R.id.rootLayout)
        val constraintLayout: ConstraintLayout = findViewById(R.id.rootLayout)
        constraintLayout.removeAllViews()
        // Constraints hier sammeln
        val set = ConstraintSet()
        set.clone(constraintLayout)
        // obere und untere "Grenze" zwischen 10% und 40%
        val idHG05 = View.generateViewId()
        set.create(idHG05, ConstraintSet.HORIZONTAL_GUIDELINE)
        set.setGuidelinePercent(idHG05, 0.10f)
        val idHG10 = View.generateViewId()
        set.create(idHG10, ConstraintSet.HORIZONTAL_GUIDELINE)
        set.setGuidelinePercent(idHG10, 0.40f)
        // gridSize+1 vertikale Guidelines zwischen 5% und 95%
        val marginL = 0.05f
        val marginR = 0.05f
        val dx = (1.0f - marginL - marginR) / gridSize.toFloat()
        val ids = ArrayList<Int>(gridSize + 1)
        for (i in 0..gridSize) {
            val idG = View.generateViewId()
            set.create(idG, ConstraintSet.VERTICAL_GUIDELINE)
            set.setGuidelinePercent(idG, marginL + i * dx)
            ids.add(idG)
        }
        // gridSize Buttons zwischen den Guidelines i und i+1
        for (i in 0 until gridSize) {
//            val button = Button(this)
//            val idB = View.generateViewId()
//            button.id = idB
//            button.text = "Button $i"
//            button.tag = i
//            button.setOnClickListener { v: View -> onClickButton(v) } // Methodenreferenz
            val idB = View.generateViewId()
            val button = Button(this).apply {
                id = idB
                text = "Button $i"
                tag = i
                setOnClickListener { v: View -> onClickButton(v) }
            }
            constraintLayout.addView(button)
            //gridButtons!!.add(button)
            gridButtons.add(button)
            set.connect(idB, ConstraintSet.LEFT, ids[i], ConstraintSet.RIGHT, 0)
            set.connect(idB, ConstraintSet.RIGHT, ids[i + 1], ConstraintSet.LEFT, 0)
            set.connect(idB, ConstraintSet.TOP, idHG05, ConstraintSet.BOTTOM, 0)
            set.connect(idB, ConstraintSet.BOTTOM, idHG10, ConstraintSet.TOP, 0)
        }
        // und alle Constraints aktivieren
        set.applyTo(constraintLayout)
    }

    private fun onClickButton(v: View) {
        val index = v.tag as Int
        Toast.makeText(this, "Button $index pressed!", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // getMenuInflater().inflate(R.menu.menu_main, menu)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_size3 -> buildGrid(3)
            R.id.action_size4 -> buildGrid(4)
            R.id.action_size5 -> buildGrid(5)
        }
        return super.onOptionsItemSelected(item)
    }

}