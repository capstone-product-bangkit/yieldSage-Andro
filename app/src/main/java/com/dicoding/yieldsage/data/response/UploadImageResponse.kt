package com.dicoding.yieldsage.data.response

import com.google.gson.annotations.SerializedName

data class UploadImageResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("errors")
	val errors: Any? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
