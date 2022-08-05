package com.example.fragmentstest.di

import com.example.fragmentstest.MainActivity
import com.example.fragmentstest.fragments.FragmentDisplay
import com.example.fragmentstest.fragments.FragmentList
import com.example.fragmentstest.views.FragmentDisplayView
import com.example.fragmentstest.views.MainActivityView
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentDisplayModule {

    @Binds
    internal abstract fun bindFragmentDisplay(view: FragmentDisplay):
            FragmentDisplayView

}