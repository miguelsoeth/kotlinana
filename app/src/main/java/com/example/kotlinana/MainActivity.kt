package com.example.kotlinana

import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import com.example.kotlinana.databinding.ActivityMainBinding
import com.example.kotlinana.util.BarcodeUtil
import com.example.kotlinana.util.ImageUtil
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)

        binding.btnScan.setOnClickListener {
            scanCode()
        }

        binding.btnShare.setOnClickListener {
            try {
                val bitmap = ImageUtil.getBitmapFromView(binding.barcodeLinearLayout)
                val imageUri: Uri = ImageUtil.saveBitmapToFile(this, bitmap)
                ImageUtil.shareImage(this, imageUri)
            } catch (e: Exception) {
                Toast.makeText(this, "Não há imagem", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun scanCode() {
        val options = ScanOptions().apply {
            setPrompt("Leia o código")
            setBeepEnabled(false)
            setOrientationLocked(true)
            setCaptureActivity(CaptureActivity::class.java)
        }
        barLauncher.launch(options)
    }

    private val barLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            binding.scanResult.text = result.contents.toString()
            val img = BarcodeUtil.generateBarcode(result.contents.toString())
            binding.barcodeImageView.setImageBitmap(img);
        }
    }

}
