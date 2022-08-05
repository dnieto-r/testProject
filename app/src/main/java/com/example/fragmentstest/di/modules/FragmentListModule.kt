package com.example.fragmentstest.di.modules

import android.app.Application
import android.content.Context
import com.example.fragmentstest.MyApplication
import com.example.fragmentstest.databases.FileStorage
import com.example.fragmentstest.databases.RoomLocalDBStorage
import com.example.fragmentstest.interactors.SearchUsersUseCase
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.views.FragmentListView
import com.example.fragmentstest.views.MainActivityView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FragmentListModule {

    @Provides
    internal fun provideFragmentListView(listView: FragmentListView): FragmentListView =
        listView

    @Provides
    internal fun provideSearchUsersUseCase(searchUsersUseCase: SearchUsersUseCase): SearchUsersUseCase =
        searchUsersUseCase
}