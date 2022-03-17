package by.godevelopment.newsappsample.data

import javax.inject.Inject

class NewsDataSource @Inject constructor(
    private val newsApi: NewsApi
) {
    suspend fun fetchAllNews() = newsApi.fetchAllNews(KeyApi.key)
}