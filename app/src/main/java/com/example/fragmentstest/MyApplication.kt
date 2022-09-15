package com.example.fragmentstest

import com.example.fragmentstest.di.DaggerAppComponent
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.Group
import dagger.android.DaggerApplication
import javax.inject.Inject

class MyApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        applicationInjector().inject(this)
    }

    public override fun applicationInjector() = DaggerAppComponent
        .factory()
        .create(this)

}
