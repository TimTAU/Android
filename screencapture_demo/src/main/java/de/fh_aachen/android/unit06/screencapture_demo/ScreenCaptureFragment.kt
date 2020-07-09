package de.fh_aachen.android.unit06.screencapture_demo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

private const val STATE_RESULT_CODE = "result_code"
private const val STATE_RESULT_DATA = "result_data"
private const val REQUEST_MEDIA_PROJECTION = 1

class ScreenCaptureFragment : Fragment(), View.OnClickListener {
    private var resultCode = 0
    private var resultData: Intent? = null

    private lateinit var surface: Surface
    private lateinit var surfaceView: SurfaceView
    private var toggleButton: Button? = null

    private var screenDensity = 0
    private var mediaProjection: MediaProjection? = null
    private var virtualDisplay: VirtualDisplay? = null
    private var mediaProjectionManager: MediaProjectionManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            resultCode = savedInstanceState.getInt(STATE_RESULT_CODE)
            resultData = savedInstanceState.getParcelable(STATE_RESULT_DATA)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_screen_capture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        surfaceView = view.findViewById(R.id.surface) ?: throw Exception("SurfaceView not existing")
        surface = surfaceView.holder.surface
        toggleButton = view.findViewById<Button>(R.id.toggle).apply { setOnClickListener(this@ScreenCaptureFragment) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val metrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(metrics)
        screenDensity = metrics.densityDpi
        mediaProjectionManager = activity!!.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (resultData != null) {
            outState.putInt(STATE_RESULT_CODE, resultCode)
            outState.putParcelable(STATE_RESULT_DATA, resultData)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.toggle -> if (virtualDisplay == null) {
                startScreenCapture()
            } else {
                stopScreenCapture()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            if (resultCode != Activity.RESULT_OK) {
                Toast.makeText(activity, R.string.user_cancelled, Toast.LENGTH_SHORT).show()
                return
            }
            this.resultCode = resultCode
            this.resultData = data
            setUpMediaProjection()
            setUpVirtualDisplay()
        }
    }

    override fun onPause() {
        super.onPause()
        stopScreenCapture()
    }

    override fun onDestroy() {
        super.onDestroy()
        tearDownMediaProjection()
    }

    private fun setUpMediaProjection() {
        mediaProjection = mediaProjectionManager!!.getMediaProjection(resultCode, resultData!!)
    }

    private fun tearDownMediaProjection() {
        if (mediaProjection != null) {
            mediaProjection!!.stop()
            mediaProjection = null
        }
    }

    private fun startScreenCapture() {
        if (activity == null) {
            return
        }
        if (mediaProjection != null) {
            setUpVirtualDisplay()
        } else if (resultCode != 0 && resultData != null) {
            setUpMediaProjection()
            setUpVirtualDisplay()
        } else {
            // This initiates a prompt dialog for the user to confirm screen projection.
            startActivityForResult(
                    mediaProjectionManager!!.createScreenCaptureIntent(),
                    REQUEST_MEDIA_PROJECTION)
        }
    }

    private fun setUpVirtualDisplay() {
        virtualDisplay = mediaProjection!!.createVirtualDisplay("ScreenCapture",
                surfaceView!!.width, surfaceView!!.height, screenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                surface, null, null)
        toggleButton!!.setText(R.string.stop)
    }

    private fun stopScreenCapture() {
        if (virtualDisplay == null) {
            return
        }
        virtualDisplay!!.release()
        virtualDisplay = null
        toggleButton!!.setText(R.string.start)
    }

}
