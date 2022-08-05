package com.example.fragmentstest.di

import com.example.fragmentstest.MainActivity
import com.example.fragmentstest.fragments.FragmentDisplay
import com.example.fragmentstest.fragments.FragmentList
import com.example.fragmentstest.views.FragmentListView
import com.example.fragmentstest.views.MainActivityView
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentListModule {

    @Binds
    internal abstract fun bindFragmentList(view: FragmentList):
            FragmentListView

    @Binds
    internal abstract fun bindMainActivityView(view: MainActivity):
            MainActivityView

}