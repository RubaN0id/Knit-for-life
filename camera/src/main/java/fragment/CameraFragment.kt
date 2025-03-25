package fragment


import analyzer.ColorAnalyzer
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_NORMAL
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.knitforlife.camera.R
import ru.knitforlife.camera.databinding.FragmentCameraBinding
import viewmodel.CameraViewModel
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import androidx.core.graphics.toColorInt
import androidx.fragment.app.activityViewModels


@AndroidEntryPoint
class CameraFragment @Inject constructor() : Fragment(R.layout.fragment_camera) {

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA
        ).toTypedArray()

        const val SUCCESS_RESULT_CODE = 15
    }

    val cameraViewModel: CameraViewModel by activityViewModels()

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var safeContext: Context

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraProvider: ProcessCameraProvider

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorEventListener: SensorEventListener
    private var tiltSensor: Sensor? = null



    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                cameraViewModel.color.collect { color ->

                    if (color != null) {
                        binding.tvColor.text = color
                        binding.tvColor.setBackgroundColor(color.toColorInt())
                    }

                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }


        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        sensorManager = requireActivity().getSystemService(SensorManager::class.java)!!
        tiltSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
        }, ContextCompat.getMainExecutor(safeContext))

        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                //nothing to do
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                //nothing to do
            }
        }

        binding.takeColorButton.setOnClickListener {
            takeColor()

        }

        animate(binding.aim)


        val view = binding.root
        return view
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
                    safeContext,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun takeColor() {
        cameraViewModel.save()

        Toast.makeText(
            safeContext,
            "Color saved!",
            Toast.LENGTH_SHORT
        ).show()
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()


            val preview = Preview.Builder()
                .build()
                .also {
                    it.surfaceProvider = binding.previewFragmentView.surfaceProvider
                }


            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

//            imageAnalysis.setAnalyzer(
//                Executors.newSingleThreadExecutor(),
//                ColorAnalyzer(cameraViewModel)
//            )

            imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor())
            { imageProxy ->

                val colors = ColorAnalyzer.getRGBfromYUV(imageProxy)
                var hexColor = String.format(
                    "#%02x%02x%02x",
                    colors.first.toInt(),
                    colors.second.toInt(),
                    colors.third.toInt()
                )
//            Log.d("test", "hexColor: $hexColor")
                cameraViewModel.takeColor(hexColor)

                imageProxy.close()
            }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, imageAnalysis, preview
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(safeContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun animate(view: ImageView) {
        val drawable = view.drawable
        if (drawable is Animatable) {
            (drawable as Animatable).start()
        }
    }


}
