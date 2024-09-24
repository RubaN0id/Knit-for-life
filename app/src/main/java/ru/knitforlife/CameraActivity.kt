package ru.knitforlife

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_NORMAL
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import ru.knitforlife.databinding.ActivityCameraBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var imageCapture: ImageCapture



    private lateinit var sensorManager: SensorManager
    private lateinit var sensorEventListener: SensorEventListener
    private var tiltSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }


        cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        sensorManager = getSystemService(SensorManager::class.java)
        tiltSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
        }, ContextCompat.getMainExecutor(this))

        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
//                val tilt = event.values[2]
//                binding.errorTextView.visibility = if (abs(tilt) > 2) View.VISIBLE else View.GONE
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                //nothing to do
            }
        }

        binding.takeColorButton.setOnClickListener {
//            takePhoto()
        }
    }


    override fun onResume() {
        super.onResume()
        tiltSensor?.let {
            sensorManager.registerListener(sensorEventListener, it, SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        tiltSensor?.let { sensorManager.unregisterListener(sensorEventListener) }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

//    @SuppressLint("MissingPermission")
//    private fun takePhoto() {
//        getLastLocation { location ->
//            Log.d("LOCATION", location.toString())
//
//            val folderPath = "${filesDir.absolutePath}/photos/"
//            val folder = File(folderPath)
//            if (!folder.exists()) {
//                folder.mkdirs()
//            }
//            val filePath = folderPath + SimpleDateFormat(FILENAME_FORMAT, Locale.getDefault()).format(Date())
//
//
//            val outputFileOptions = ImageCapture.OutputFileOptions.Builder(File(filePath))
//                .setMetadata(ImageCapture.Metadata().also { it.location = location })
//                .build()
//
//            val imageSavedCallback = object : ImageCapture.OnImageSavedCallback {
//                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                    Toast.makeText(
//                        this@CameraActivity,
//                        "Saved!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    setResult(SUCCESS_RESULT_CODE)
//                    finish()
//                }
//
//                override fun onError(exception: ImageCaptureException) {
//                    Toast.makeText(
//                        this@CameraActivity,
//                        "Error save photo",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//
//            }
//            imageCapture.takePicture(
//                outputFileOptions,
//                ContextCompat.getMainExecutor(this),
//                imageSavedCallback
//            )
//        }
//    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }

//            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview
//                    , imageCapture
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {

        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA,
        ).toTypedArray()

        const val SUCCESS_RESULT_CODE = 15
    }
}