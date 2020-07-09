package de.fh_aachen.android.unit05.gps_demo

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

// see http://developer.android.com/reference/android/location/LocationManager.html

const val MIN_TIME = 3000L
// minDistance: minimum distance between location updates, in meters
const val MIN_DISTANCE = 5L

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), LocationListener {
    var isNetworkEnabled = false
    var isGPSEnabled = false
    lateinit var locationManager: LocationManager

    var location: Location? = null

    // geogr. Breite (0 = Aequator)
    val latitude: Double
        get() = location?.latitude ?: 0.0 // if (location != null) location.latitude else 0.0

    // geogr. Laenge (0 = Greenwich)
    val longitude: Double
        get() = location?.longitude ?: 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLocation()

        textView5.text = "Mode: Network? $isNetworkEnabled , GPS? $isGPSEnabled"
    }

    val MY_PERMISSIONS_REQUEST_LOCATIONS = 1

    private fun initLocation(): Location? {
        try {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            //val providers = locationManager.getProviders(true)
            for (providerName in locationManager.getProviders(true)) {
                Log.v(TAG, "provider: $providerName")
            }
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (isNetworkEnabled || isGPSEnabled) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION),
                            MY_PERMISSIONS_REQUEST_LOCATIONS)
                    return null
                }
            }
            requestLocation()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return location
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATIONS -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    requestLocation()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }
    }

    private fun requestLocation() {
        if (isNetworkEnabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME,
                    MIN_DISTANCE.toFloat(), this)
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        } else if (isGPSEnabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME,
                    MIN_DISTANCE.toFloat(), this)
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }
    }

    override fun onLocationChanged(location: Location) {
        this.location = location

        textView6.text = "${location.provider}"
        textView4.text = "$latitude"
        textView3.text = "$longitude"

        Log.i(TAG, "$latitude : $longitude")
    }

    override fun onProviderDisabled(provider: String) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

}
