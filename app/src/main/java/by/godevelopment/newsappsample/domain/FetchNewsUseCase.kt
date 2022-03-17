package by.godevelopment.newsappsample.domain

import by.godevelopment.newsappsample.data.datamodel.NewsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<NewsModel> = flow {
        emit(repository.fetchAllNews())
    }
}