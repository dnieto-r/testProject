package com.example.fragmentstest.presenters

import com.example.fragmentstest.views.SelectGroupDialogView
import javax.inject.Inject

class SelectGroupDialogPresenter @Inject constructor(
    var displayView: SelectGroupDialogView?,
) {

    fun selectGroup(position: Int) {
        displayView?.onSelectGroup(position)
    }

}
