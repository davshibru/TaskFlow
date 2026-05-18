package com.davidshibru.taskflow.features.profile.demo

import com.davidshibru.taskflow.features.profile.domain.repositories.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProfileDemoModule {

    @Binds
    fun bindProfileRepository(
        impl: DemoProfileRepository
    ): ProfileRepository
}