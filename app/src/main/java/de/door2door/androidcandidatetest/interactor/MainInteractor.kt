package de.door2door.androidcandidatetest.interactor

import de.door2door.androidcandidatetest.model.RemoteModel

interface MainInteractor {
    fun getAllRoutes() : List<RemoteModel.Route>

    fun getRoute(id : Int) : RemoteModel.Route
}