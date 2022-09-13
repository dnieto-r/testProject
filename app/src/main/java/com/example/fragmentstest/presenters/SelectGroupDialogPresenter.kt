package com.example.fragmentstest.presenters

import com.example.fragmentstest.views.SelectGroupDialogView

class SelectGroupDialogPresenter(
    var displayView: SelectGroupDialogView?,
) {

    fun selectGroup(position: Int) {
        displayView?.onSelectGroup(position)
    }

}
