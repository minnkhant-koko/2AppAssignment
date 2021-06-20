package com.eds.a2appstudiointerviewassignment.di

import com.eds.a2appstudiointerviewassignment.repository.WebLinkRepositoryImpl
import com.eds.a2appstudiointerviewassignment.repository.WebLinkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideWebLinkRepository(): WebLinkRepository =
            WebLinkRepositoryImpl()
}