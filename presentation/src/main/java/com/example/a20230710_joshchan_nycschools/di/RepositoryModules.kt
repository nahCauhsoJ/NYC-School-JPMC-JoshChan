package com.example.a20230710_joshchan_nycschools.di

import com.example.a20230710_joshchan_nycschools.repository.NYCRepository
import com.example.a20230710_joshchan_nycschools.repository.NYCRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModules {
    @Binds
    @Singleton
    abstract fun bindsNycRepository(impl: NYCRepositoryImpl): NYCRepository
}