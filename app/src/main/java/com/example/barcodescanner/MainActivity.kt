package com.example.barcodescanner

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.example.barcodescanner.Constant.Companion.BASE_URL
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.tekst
import retrofit2.Call
import retrofit2.Response


class MainActivity :AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tekst.setOnClickListener {
            scanProduct("1234",this)
        }
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)==
                PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 123)
        }else{
            startScanning()
        }
    }

    private fun startScanning(){
        codeScanner = CodeScanner(this, scanner_view)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                val ean = it.text.toString()
                 scanProduct(ean,this)
            }
        }
        tekst.setOnClickListener {
            scanProduct("1234",this)
        }
        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Camera permisson granted", Toast.LENGTH_SHORT).show()
                startScanning()
            }else{
                Toast.makeText(this, "Camera permisson denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()

    }
    fun scanProduct(ean: String, mainActivity: MainActivity){
        var productReq = ProductRequest()
        productReq.ean = ean

        val retro = Retro().getRetroInstance(BASE_URL).create(ScanAPI::class.java)
        retro.scanProduct(productReq).enqueue(object : retrofit2.Callback<ProductResponse>{
            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {

            }
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                //Toast.makeText(this@MainActivity,"GOOD",Toast.LENGTH_SHORT)
                val productResp = response.body()!!
                Intent(mainActivity,Second::class.java).also {
                    it.putExtra("VALUES", productResp.values)
                    it.putExtra("MESSAGE",productResp.message)
                    it.putExtra("EAN",ean)
                    startActivity(it)
                }
            }

        })

    }
}



