package com.example.fragmentstest.di

import android.app.Application
import android.content.Context
import com.example.fragmentstest.MyApplication
import com.example.fragmentstest.databases.FileStorage
import com.example.fragmentstest.databases.LocalStorage
import com.example.fragmentstest.databases.RoomLocalDBStorage
import com.example.fragmentstest.interfaces.Storage
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideApplication(application: MyApplication):
            Application = application

    @Provides
    fun provideContext(application: MyApplication): Context =
        application.applicationContext

    @Provides
    fun provideStorage(context: Context): Storage =
        RoomLocalDBStorage(context)

}
