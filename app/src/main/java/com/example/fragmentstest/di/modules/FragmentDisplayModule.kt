package com.example.fragmentstest.di.modules

import com.example.fragmentstest.fragments.FragmentDisplay
import com.example.fragmentstest.views.FragmentDisplayView
import dagger.Binds
import dagger.Module

@Module
abstract class FragmentDisplayModule {

    @Binds
    internal abstract fun bindFragmentDisplay(view: FragmentDisplay):
            FragmentDisplayView

}