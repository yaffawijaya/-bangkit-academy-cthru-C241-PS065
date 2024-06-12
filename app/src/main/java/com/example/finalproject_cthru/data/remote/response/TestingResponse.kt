package com.example.finalproject_cthru.data.remote.response

import com.google.gson.annotations.SerializedName

data class TestingResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("detection")
	val detection: String? = null,

	@field:SerializedName("confidence")
	val confidence: Any? = null,

	@field:SerializedName("id")
	val id: String? = null
)
