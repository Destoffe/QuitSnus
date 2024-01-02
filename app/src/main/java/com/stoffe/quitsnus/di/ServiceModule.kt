package com.stoffe.quitsnus.di

import com.stoffe.quitsnus.misc.AccountService
import com.stoffe.quitsnus.misc.AccountServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
}