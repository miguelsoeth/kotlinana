package com.example.kotlinana.util

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class BarcodeUtil {
    companion object {
        fun generateBarcode(input: String): Bitmap? {
            return try {
                val barcodeEncoder = BarcodeEncoder()
                // Use Code 128, which is more compact than Code 39
                val bitMatrix = barcodeEncoder.encode(input, BarcodeFormat.CODE_128, 600, 300)
                barcodeEncoder.createBitmap(bitMatrix)
            } catch (e: WriterException) {
                e.printStackTrace()
                null // Handle the exception as needed
            }
        }
    }
}
