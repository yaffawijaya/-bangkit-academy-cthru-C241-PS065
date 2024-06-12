package com.example.finalproject_cthru.data.remote.response

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ScanData(

	@field:SerializedName("cataract_confidence")
	val cataractConfidence: Any? = null,

	@field:SerializedName("cataract_prediction")
	val cataractPrediction: String? = null,

	@field:SerializedName("eye_prediction")
	val eyePrediction: String? = null,

	@field:SerializedName("eye_confidence")
	val eyeConfidence: Any? = null,

	@field:SerializedName("id")
	val id: String? = null
)
