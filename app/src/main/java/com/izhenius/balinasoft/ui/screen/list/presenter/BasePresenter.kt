package com.izhenius.balinasoft.ui.screen.list.presenter

interface BasePresenter {
    fun loadData()
    fun onDataLoadResult(isSuccess: Boolean)
}
