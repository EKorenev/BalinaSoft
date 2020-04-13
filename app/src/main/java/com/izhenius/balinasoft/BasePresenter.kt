package com.izhenius.balinasoft

interface BasePresenter {
    fun setView(view: BaseView)
    fun loadData()
    fun onDataLoadResult(isSuccess: Boolean)
}
