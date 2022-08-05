package com.example.fragmentstest.di

import android.content.Context
import com.example.fragmentstest.MyApplication
import com.example.fragmentstest.di.modules.FragmentListModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    FragmentBuilder::class,
    FragmentDisplayModule::class
])
interface AppComponent : AndroidInjector<MyApplication> {

    fun context(): Context

    @Component.Factory
    interface Builder {
        fun create(@BindsInstance app: MyApplication): AppComponent
    }

}