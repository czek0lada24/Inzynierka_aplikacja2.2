package com.example.barcodescanner

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductRequest {
    @SerializedName("ean")
    @Expose
    var ean: String? = null
}