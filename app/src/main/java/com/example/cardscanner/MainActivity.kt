package com.example.cardscanner



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ir.arefdev.irdebitcardscanner.ScanActivity


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    lateinit var scanButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scanButton = findViewById(R.id.scan_button)

        ScanActivity.warmUp(this)

        scanButton.setOnClickListener{
            ScanActivity.start(this)
        }

    }


  //  fun onClick(v: View) {
       // ScanActivity.start(this)
//        if (v.id == R.id.scan_button) {
//
//        } else if (v.id == R.id.scanCardDebug) {
//            ScanActivity.startDebug(this)
//        } else if (v.id == R.id.scanCardAltText) {
//            ScanActivity.start(
//                this, "Debit Card Scan",
//                "Position your card in the frame so the card number is visible"
//            )
//        }
   // }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ScanActivity.isScanResult(requestCode)) {
            if (resultCode == RESULT_OK && data != null) {
                val scanResult = ScanActivity.debitCardFromResult(data)
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("cardNumber", scanResult.number)
                intent.putExtra("cardExpiryMonth", scanResult.expiryMonth)
                intent.putExtra("cardExpiryYear", scanResult.expiryYear)
                startActivity(intent)
                Log.d("MMMMMM", scanResult.number)
            } else if (resultCode == ScanActivity.RESULT_CANCELED) {
                val fatalError = data!!.getBooleanExtra(ScanActivity.RESULT_FATAL_ERROR, false)
                if (fatalError) {
                    Log.d(TAG, "fatal error")
                } else {
                    Log.d(TAG,
                        "The user pressed the back button"
                    )
                }
            }
        }
    }

}