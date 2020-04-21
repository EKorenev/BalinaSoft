package com.izhenius.balinasoft.ui.screen.list.view

import android.content.Context

interface BaseView {
    fun getContext(): Context
    fun updateFields()
    fun showToast(text: String)
}
