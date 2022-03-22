package com.example.barcodescanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_second.*

class Second : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val scan = intent.getStringExtra("VALUES")
        val message=intent.getStringExtra("MESSAGE")
        val ean = intent.getStringExtra("EAN")
        if(message=="all_ok") {
            add_product.visibility = View.INVISIBLE
            val results = scan?.substringAfter("', '")?.substringBefore("')]")
            val name = results?.substringBefore("', '")
            val bin = results?.substringAfter("', '")
            nametv.text = name
            bintv.text = bin
            when(bin){
                "Plastik"->image_type.setImageResource(R.mipmap.plastic_foreground)
                "Szkło" -> image_type.setImageResource(R.mipmap.glass_foreground)
                "Metal" -> image_type.setImageResource(R.mipmap.metal_foreground)
                "Papier" -> image_type.setImageResource(R.mipmap.paper_foreground)
            }
        }else if(message=="no_product"){
            add_product.visibility = View.VISIBLE
            nametv.text="Zeskanowany produkt nie występuje w bazie."
            nametv.textSize= 25F
            bintv.text="Zeskanuj inny artykuł."
            bintv.textSize = 25F
            add_product.setOnClickListener{
                Intent(this,AddProduct::class.java).also {
                    it.putExtra("EAN",ean)
                    startActivity(it)
                }
            }
        }

        ScanBtn.setOnClickListener {
            finish()
        }
    }
}