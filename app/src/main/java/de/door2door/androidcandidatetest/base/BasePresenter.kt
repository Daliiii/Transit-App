package de.door2door.androidcandidatetest.base


abstract class BasePresenter<out V : BaseView>(protected val view: V) {
    open fun onViewCreated(){}
}