package com.dicoding.yieldsage.data.response

import com.google.gson.annotations.SerializedName

data class GetResultResponse(

	@field:SerializedName("data")
	val data: List<DataResult> = emptyList(),

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("errors")
	val errors: Any? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataResult(

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
