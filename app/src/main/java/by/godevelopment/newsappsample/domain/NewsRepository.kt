package by.godevelopment.newsappsample.domain

import by.godevelopment.newsappsample.data.datamodel.NewsModel

interface NewsRepository {

    suspend fun fetchAllNews(): NewsModel
}