package com.dicoding.yieldsage.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("errors")
	val errors: Any? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DataLogin? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class User(

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class DataLogin(

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("refreshToken")
	val refreshToken: String? = null
)
