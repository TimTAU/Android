package de.fh_aachen.android.unit05.sensor_demo

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorAcc: Sensor

    private lateinit var adapter: ArrayAdapter<String>
    private val entries = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ArrayAdapter(this, android.R.layout.simple_list_item_1, entries).apply {
            adapter = this
            listView1.adapter = this
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // assume here, that it always exists!!!
        sensorAcc = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        sensorManager.getSensorList(Sensor.TYPE_ALL).forEach {
            entries.add("${it.name} : ${it.type}")
        }
        adapter.notifyDataSetChanged()
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor == sensorAcc) {
            textView2.text = event.values[0].toString()
            textView4.text = event.values[1].toString()
            textView6.text = event.values[2].toString()
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensorAcc, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
