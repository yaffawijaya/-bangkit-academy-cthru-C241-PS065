package com.example.finalproject_cthru.data.remote.response

import android.content.Context
import com.example.finalproject_cthru.R
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.InputStreamReader

data class ArticleResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
) {
	companion object {
		fun setMockData(context: Context): List<ArticlesItem>? {
			val inputStream = context.resources.openRawResource(R.raw.article)
			val reader = InputStreamReader(inputStream)
			val articleResponse = Gson().fromJson(reader, ArticleResponse::class.java)
			return articleResponse.articles as List<ArticlesItem>?
		}
	}
}

data class ArticlesItem(

	@field:SerializedName("publishedAt")
	val publishedAt: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("urlToImage")
	val urlToImage: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("source")
	val source: Source? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("content")
	val content: Any? = null
)

data class Source(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
