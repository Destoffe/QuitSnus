package com.stoffe.quitsnus.di

import com.stoffe.quitsnus.misc.AccountService
import com.stoffe.quitsnus.misc.AccountServiceImpl
import com.stoffe.quitsnus.misc.StorageService
import com.stoffe.quitsnus.misc.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
}