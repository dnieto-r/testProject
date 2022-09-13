package com.example.fragmentstest.di

import com.example.fragmentstest.di.modules.FragmentDisplayModule
import com.example.fragmentstest.di.modules.FragmentListModule
import com.example.fragmentstest.fragments.FragmentDisplay
import com.example.fragmentstest.fragments.FragmentList
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector(modules = [(FragmentDisplayModule::class)])
    internal abstract fun provideDisplayBuilder(): FragmentDisplay

    @ContributesAndroidInjector(modules = [(FragmentListModule::class)])
    internal abstract fun provideListBuilder(): FragmentList
}