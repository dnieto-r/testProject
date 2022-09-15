package com.example.fragmentstest.di

import com.example.fragmentstest.MainActivity
import com.example.fragmentstest.di.modules.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    internal abstract fun provideMainActivity(): MainActivity

}