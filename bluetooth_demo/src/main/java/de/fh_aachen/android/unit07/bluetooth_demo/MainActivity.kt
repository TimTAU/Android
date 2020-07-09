package de.fh_aachen.android.unit07.bluetooth_demo

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
private const val REQUEST_ENABLE_BT = 1

class MainActivity : AppCompatActivity() {

    private var btAdapter: BluetoothAdapter? = null

    private val isBtInit :Boolean
        get() = btAdapter != null
    private val isBtEnabled :Boolean
        get() = btAdapter?.isEnabled == true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // kann null sein!
        btAdapter = BluetoothAdapter.getDefaultAdapter()
        if (btAdapter==null) {
            Log.e(TAG, "bluetooth was not available")
        }

        updateState()
        updateScan(false)
    }

    // receiver an- und abmelden
    override fun onResume() {
        super.onResume()
        if (isBtInit)
            registerReceiver(btReceiver, btFilter)
    }

    override fun onPause() {
        if (isBtInit) {
            cancelBtDiscovery()
            unregisterReceiver(btReceiver)
        }
        super.onPause()
    }

    fun updateState() {
        checkBox.isChecked = isBtEnabled //isBtInit && btAdapter!!.isEnabled
    }

    fun setBtOn(view: View?) {
        enableBt(true)
    }

    fun setBtOff(view: View?) {
        enableBt(false)
    }

    private fun cancelBtDiscovery() {
        btAdapter?.apply { if (isDiscovering) cancelDiscovery() }
    }

    private fun enableBt(enable: Boolean) {
        if (!isBtInit) {
            Log.e(TAG, "bluetooth was not initialized")
            return
        }

        cancelBtDiscovery()
        if (enable) {
            if (!isBtEnabled) {
                // entweder so, oder über die Anfrage beim User
                // if (!btAdapter.enable())
                //    Log.e(app,"bluetooth could not be enabled");
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }
        } else {
            if (isBtEnabled) {
                btAdapter?.disable()
            }
        }
        updateState()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            Log.v(TAG, "bluetooth is enabled")
            updateState()
        }
    }

    fun updateScan(state: Boolean) {
        checkBoxScan.isChecked = state
    }

    fun startScan(view: View?) {
        if (!isBtInit) {
            Log.e(TAG, "bluetooth was not initialized")
            return
        }
        cancelBtDiscovery()
        btAdapter?.startDiscovery()
    }

    private val btReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Gerät gefunden
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    val txt = ("device found, name:" + device!!.name
                            + ", addr.:" + device.address
                            + ", state:" + if (device.bondState != BluetoothDevice.BOND_BONDED) "?" else "paired")
                    Log.v(TAG, txt)
                    Toast.makeText(this@MainActivity, txt, Toast.LENGTH_LONG).show()
                }
                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                    // bond status hat sich geändert, device wie oben
                    Log.v(TAG, "bond changed")
                }
                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    // Suche wurde gestartet, ca. 12s.
                    Log.v(TAG, "discovery started")
                    updateScan(true)
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    // Suche wurde beendet
                    Log.v(TAG, "discovery finish")
                    updateScan(false)
                }
                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    // Zustand des Adapters hat sich gändert
                    // beachte: es gibt weitere Übergangszustände
                    when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
                        BluetoothAdapter.STATE_OFF -> {
                            Log.v(TAG, "Bluetooth off")
                            updateState()
                        }
                        BluetoothAdapter.STATE_ON -> {
                            Log.v(TAG, "Bluetooth on")
                            updateState()
                        }
                    }
                }
            }
        }
    }

    private val btFilter = IntentFilter().apply {
        addAction(BluetoothDevice.ACTION_FOUND)
        addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
    }


}

