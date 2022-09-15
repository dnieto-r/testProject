package com.example.fragmentstest.di.modules

import com.example.fragmentstest.dialogs.groups.SelectGroupDialogFragment
import com.example.fragmentstest.fragments.FragmentList
import com.example.fragmentstest.views.FragmentListView
import com.example.fragmentstest.views.SelectGroupDialogView
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class SelectGroupDialogFragmentModule {

    @Binds
    internal abstract fun provideSelectGroupDialog(
        selectGroupDialog:
        SelectGroupDialogFragment
    ): SelectGroupDialogView


}