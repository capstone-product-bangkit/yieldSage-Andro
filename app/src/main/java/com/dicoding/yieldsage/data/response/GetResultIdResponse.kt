package com.dicoding.yieldsage.data.response

import com.google.gson.annotations.SerializedName

data class GetResultIdResponse(

	@field:SerializedName("data")
	val data: DetailResult? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("errors")
	val errors: Boolean? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class ImageResultItem(

	@field:SerializedName("yield_individual")
	val yieldIndividual: List<Int?>? = null,

	@field:SerializedName("cpa_individual")
	val cpaIndividual: List<Int?>? = null,

	@field:SerializedName("total_yield")
	val totalYield: Int? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("age_individual")
	val ageIndividual: List<Int?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("age_average")
	val ageAverage: Any? = null,

	@field:SerializedName("tree_count")
	val treeCount: Float? = null,

	@field:SerializedName("cpa_average")
	val cpaAverage: Any? = null
)

data class DetailResult(

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("age_average")
	val ageAverage: Any? = null,

	@field:SerializedName("total_yield")
	val totalYield: Int? = null,

	@field:SerializedName("tree_count")
	val treeCount: Int? = null,

	@field:SerializedName("cpa_average")
	val cpaAverage: Any? = null,

	@field:SerializedName("image_content")
	val imageContent: List<ImageResultItem> = emptyList()
)
