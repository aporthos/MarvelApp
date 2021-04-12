package com.globant.shared.interfaces

import android.view.View

interface OnClickListener<IT> {
    fun onClicked(v: View, item: IT)
    fun onChecked(v: View, item: IT)
}