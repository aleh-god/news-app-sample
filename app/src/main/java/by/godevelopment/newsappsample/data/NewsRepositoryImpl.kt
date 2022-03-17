package by.godevelopment.newsappsample.data

import by.godevelopment.newsappsample.domain.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsDataSource: NewsDataSource
) : NewsRepository {

    override suspend fun fetchAllNews() = newsDataSource.fetchAllNews()
}