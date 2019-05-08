package de.door2door.androidcandidatetest.features.list.presenter

import de.door2door.androidcandidatetest.base.BasePresenter
import de.door2door.androidcandidatetest.features.list.mapper.RouteModelMapper
import de.door2door.androidcandidatetest.features.list.model.RouteModel
import de.door2door.androidcandidatetest.features.list.view.ListView
import de.door2door.androidcandidatetest.interactor.MainInteractorImp
import de.door2door.androidcandidatetest.utils.Logger

class ListPresenter(
    listView: ListView,
    private val mapper: RouteModelMapper,
    private var mainInteractor: MainInteractorImp
) : BasePresenter<ListView>(listView) {

    override fun onViewCreated() {
        Logger.init()
        view.initToolbar()
        val routeModels = ArrayList<RouteModel>()
        mainInteractor.getAllRoutes().map {
            routeModels.add(mapper.mapRouteModel(it))
        }
        view.initRecyclerView(routeModels)
    }
}