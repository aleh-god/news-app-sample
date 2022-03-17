package by.godevelopment.newsappsample.di

import by.godevelopment.newsappsample.data.NewsRepositoryImpl
import by.godevelopment.newsappsample.domain.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    abstract fun bindRepositoryToImpl(newsRepositoryImpl: NewsRepositoryImpl) : NewsRepository
}