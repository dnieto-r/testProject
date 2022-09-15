package com.example.fragmentstest.di.modules

import com.example.fragmentstest.MainActivity
import com.example.fragmentstest.fragments.FragmentDisplay
import com.example.fragmentstest.views.FragmentDisplayView
import com.example.fragmentstest.views.MainActivityView
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

    @Binds
    internal abstract fun bindMainActivity(view: MainActivity):
            MainActivityView

}