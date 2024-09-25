package ru.knitforlife.analyzer

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import ru.knitforlife.viewmodel.CameraViewModel
import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit

class ColorAnalyzer (val cameraViewModel: CameraViewModel ): ImageAnalysis.Analyzer {

    private var lastAnalyzedTimestamp = 0L




    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }


    private fun getRGBfromYUV(image: ImageProxy): Triple<Double, Double, Double> {
        val planes = image.planes

        val height = image.height
        val width = image.width

        // Y
        val yArr = planes[0].buffer
        val yArrByteArray = yArr.toByteArray()
        val yPixelStride = planes[0].pixelStride
        val yRowStride = planes[0].rowStride

        // U
        val uArr = planes[1].buffer
        val uArrByteArray =uArr.toByteArray()
        val uPixelStride = planes[1].pixelStride
        val uRowStride = planes[1].rowStride

        // V
        val vArr = planes[2].buffer
        val vArrByteArray = vArr.toByteArray()
        val vPixelStride = planes[2].pixelStride
        val vRowStride = planes[2].rowStride

        val y = yArrByteArray[(height * yRowStride + width * yPixelStride) / 2].toInt() and 255
        val u = (uArrByteArray[(height * uRowStride + width * uPixelStride) / 4].toInt() and 255) - 128
        val v = (vArrByteArray[(height * vRowStride + width * vPixelStride) / 4].toInt() and 255) - 128

        val r = y + (1.370705 * v)
        val g = y - (0.698001 * v) - (0.337633 * u)
        val b = y + (1.732446 * u)

        return Triple(r,g,b)
    }


    // analyze the color
    override fun analyze(image: ImageProxy) {
        val currentTimestamp = System.currentTimeMillis()
        if (currentTimestamp - lastAnalyzedTimestamp >= TimeUnit.MILLISECONDS.toMillis(100)) {

            val colors = getRGBfromYUV(image)
            var hexColor = String.format("#%02x%02x%02x", colors.first.toInt(), colors.second.toInt(), colors.third.toInt())
//            Log.d("test", "hexColor: $hexColor")
            cameraViewModel.color.postValue(hexColor)

            lastAnalyzedTimestamp = currentTimestamp
        }

        image.close()

    }


}