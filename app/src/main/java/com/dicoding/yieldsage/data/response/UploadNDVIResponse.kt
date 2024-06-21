package com.dicoding.yieldsage.data.response

import com.google.gson.annotations.SerializedName

data class UploadNDVIResponse(

	@field:SerializedName("data")
	val data: DataUp? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("errors")
	val errors: Boolean? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataUp(

	@field:SerializedName("nir_image")
	val nirImage: String? = null,

	@field:SerializedName("average_ndvi")
	val averageNdvi: Any? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("red_image")
	val redImage: String? = null,

	@field:SerializedName("health_status")
	val healthStatus: String? = null,

	@field:SerializedName("ndvi_image")
	val ndviImage: String? = null
)
