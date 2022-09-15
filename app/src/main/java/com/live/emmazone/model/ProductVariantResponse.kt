package com.live.emmazone.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductVariant(
    @SerializedName("id")
    var id: Int, // 18
    @SerializedName("categorySizeId")
    var categorySizeId: Int, // 77
    @SerializedName("categoryColorId")
    var categoryColorId: Int, // 80
    @SerializedName("price")
    var price: String, // 600.00
    @SerializedName("quantity")
    var quantity:Int,//48
    @SerializedName("barcodeImage")
    var barCodeImage:String,
    @SerializedName("color")
    var color:String, //White
    @SerializedName("size")
    var size:String  //7Inch
): Serializable