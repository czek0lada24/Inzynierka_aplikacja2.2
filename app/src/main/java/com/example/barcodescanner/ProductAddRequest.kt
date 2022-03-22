package com.example.barcodescanner

import com.google.gson.annotations.SerializedName

data class ProductAddRequest(@SerializedName("ean") var ean : String?,
                             @SerializedName("name") var name : String?,
                             @SerializedName("bin") var bin : String?)
