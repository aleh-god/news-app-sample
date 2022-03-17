package by.godevelopment.newsappsample.data

import by.godevelopment.newsappsample.data.datamodel.NewsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines?sources=techcrunch")
    suspend fun fetchAllNews(@Query("apiKey") key: String): NewsModel
}