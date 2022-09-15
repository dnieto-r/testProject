package com.example.fragmentstest.di

import com.example.fragmentstest.di.modules.FragmentDisplayModule
import com.example.fragmentstest.di.modules.FragmentListModule
import com.example.fragmentstest.di.modules.SelectGroupDialogFragmentModule
import com.example.fragmentstest.dialogs.EditUserDialogFragment
import com.example.fragmentstest.dialogs.groups.CreateGroupDialogFragment
import com.example.fragmentstest.dialogs.groups.EditGroupDialogFragment
import com.example.fragmentstest.dialogs.groups.SelectGroupDialogFragment
import com.example.fragmentstest.dialogs.groups.SelectOptionDialogFragment
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

    @ContributesAndroidInjector
    internal abstract fun provideEditUserDialog(): EditUserDialogFragment

    @ContributesAndroidInjector
    internal abstract fun provideEditGroupDialog(): EditGroupDialogFragment

    @ContributesAndroidInjector(modules = [(SelectGroupDialogFragmentModule::class)])
    internal abstract fun provideSelectGroupDialog(): SelectGroupDialogFragment

    @ContributesAndroidInjector
    internal abstract fun provideSelectOptionDialog(): SelectOptionDialogFragment

    @ContributesAndroidInjector
    internal abstract fun provideCreateOptionDialog(): CreateGroupDialogFragment

}