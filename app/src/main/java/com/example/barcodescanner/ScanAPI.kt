package com.example.barcodescanner

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ScanAPI {
    @POST("check_product")
    fun scanProduct(@Body request: ProductRequest) : Call<ProductResponse>
    @POST("add_product")
    fun addProduct(@Body request: ProductAddRequest) : Call<ProductAddResponse>
}