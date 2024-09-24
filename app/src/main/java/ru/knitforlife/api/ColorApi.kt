package ru.knitforlife.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ColorApi {
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("id")
    suspend fun getColorName(@Query("hex") hex:String): ColorIdResponse
}

data class ColorIdResponse(val hex: Hex, val name: Name, val rgb: RGB)

data class Hex(val clean:String,val value:String)
data class Name(val value: String)
data class RGB (val r:Int, val g:Int, val b:Int)