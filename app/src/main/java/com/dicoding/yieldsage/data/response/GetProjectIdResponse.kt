package com.dicoding.yieldsage.data.response

import com.google.gson.annotations.SerializedName

data class GetProjectIdResponse(

	@field:SerializedName("data")
	val data: DataRepo? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("errors")
	val errors: Any? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataRepo(

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("image_content")
	val imageContent: List<ImageContentItem> = emptyList()
)

data class ImageContentItem(

	@field:SerializedName("yield_individual")
	val yieldIndividual: List<Any?>? = null,

	@field:SerializedName("cpa_individual")
	val cpaIndividual: List<Any?>? = null,

	@field:SerializedName("total_yield")
	val totalYield: Any? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("age_individual")
	val ageIndividual: List<Any?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("age_average")
	val ageAverage: Any? = null,

	@field:SerializedName("tree_count")
	val treeCount: Any? = null,

	@field:SerializedName("cpa_average")
	val cpaAverage: Any? = null
)
