package com.example.barcodescanner

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductResponse(@SerializedName("message") var message: String?,
                           @SerializedName("values") var values : String?)

data class ProductAddResponse(@SerializedName("message") var message: String?)
