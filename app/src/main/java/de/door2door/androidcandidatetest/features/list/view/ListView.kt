package de.door2door.androidcandidatetest.features.list.view

import de.door2door.androidcandidatetest.base.BaseView
import de.door2door.androidcandidatetest.features.list.model.RouteModel

interface ListView : BaseView {
    fun initRecyclerView(routeModels: ArrayList<RouteModel>)

    fun initToolbar()
}