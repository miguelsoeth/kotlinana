package com.example.kotlinana

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinana.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

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
