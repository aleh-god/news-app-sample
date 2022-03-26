package by.godevelopment.newsappsample.domain

import by.godevelopment.newsappsample.data.datamodel.NewsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ConvertTextToBetterViewUseCase @Inject constructor(
    private val fetchNewsUseCase: FetchNewsUseCase
) {
    operator fun invoke(): Flow<NewsModel> = fetchNewsUseCase()
        .map {
            val newList = it.articles
                .map { article ->
                    val isoTimeString = article.publishedAt
                    val goodTimeString = isoTimeString.replace(
                        "T", " ", true
                    ).dropLast(4)
                    article.copy(
                        description = "\t" + article.description,
                        publishedAt = goodTimeString
                    )
                }
            it.copy(
                articles = newList
            )
        }
}