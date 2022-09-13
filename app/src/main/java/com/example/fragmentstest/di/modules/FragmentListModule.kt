package com.example.fragmentstest.di.modules

import com.example.fragmentstest.fragments.FragmentList
import com.example.fragmentstest.views.FragmentListView
import dagger.Module
import dagger.Provides

@Module
class FragmentListModule {

    @Provides
    internal fun provideFragmentListView(fragmentList: FragmentList): FragmentListView =
        fragmentList

}