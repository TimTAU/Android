package de.fh_aachen.android.unit03.photo_demo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

private const val REQUEST_PERMISSIONS = 1
private const val REQUEST_IMAGE_CAPTURE = 2

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enableRuntimePermission()
    }

    private fun enableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Toast.makeText(this@MainActivity, "CAMERA permission allows us to access CAMERA", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(RC: Int, per: Array<String>, PResult: IntArray) {
        when (RC) {
            REQUEST_PERMISSIONS -> if (PResult.isNotEmpty() && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Permission granted, app can access CAMERA", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@MainActivity, "Permission canceled, app are not allowed to access CAMERA", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun makePhoto(view: View?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            if (resolveActivity(packageManager) != null)
                startActivityForResult(this, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            imageView.setImageBitmap(data!!.extras!!["data"] as Bitmap)
            Toast.makeText(this, "Photo!", Toast.LENGTH_SHORT).show()
        }
    }

}
