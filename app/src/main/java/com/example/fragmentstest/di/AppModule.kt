package com.example.fragmentstest.di

import android.app.Application
import android.content.Context
import com.example.fragmentstest.MainActivity
import com.example.fragmentstest.MyApplication
import com.example.fragmentstest.databases.FileStorage
import com.example.fragmentstest.databases.LocalStorage
import com.example.fragmentstest.databases.RoomLocalDBStorage
import com.example.fragmentstest.interactors.SearchUsersUseCase
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.views.FragmentListView
import com.example.fragmentstest.views.MainActivityView
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    internal fun provideContext(application: MyApplication): Context =
        application.applicationContext

    @Provides
    internal fun provideApplication(application: MyApplication):
            Application = application

    @Provides
    internal fun provideStorage(): Storage = LocalStorage()

}
