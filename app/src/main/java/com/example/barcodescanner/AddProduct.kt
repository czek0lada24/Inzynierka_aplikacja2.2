package com.example.barcodescanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.barcodescanner.Constant.Companion.BASE_URL
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_second.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AddProduct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var bin:String =""

        val ean = intent.getStringExtra("EAN")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(selected: AdapterView<*>, view: View?, position: Int, id: Long) {
                bin = selected?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        AddBtn.setOnClickListener {
            val name = nameEdit.text.toString()
            var productAddReq = ProductAddRequest(ean, name, bin)

            val retro = Retro().getRetroInstance(BASE_URL).create(ScanAPI::class.java)
            retro.addProduct(productAddReq).enqueue(object  : Callback<ProductAddResponse>{
                override fun onResponse(
                    call: Call<ProductAddResponse>,
                    response: Response<ProductAddResponse>
                ) {
                    val productResp = response.body()!!
                   Toast.makeText(this@AddProduct,productResp.message,Toast.LENGTH_SHORT).show()
                    Intent(this@AddProduct,MainActivity::class.java).also {
                        startActivity(it)
                    }
                }

                override fun onFailure(call: Call<ProductAddResponse>, t: Throwable) {
                }

            })

        }
    }
}