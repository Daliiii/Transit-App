package de.door2door.androidcandidatetest.interactor

import android.content.Context
import com.google.gson.Gson
import de.door2door.androidcandidatetest.R
import de.door2door.androidcandidatetest.model.RemoteModel

class MainInteractorImp(val context: Context) : MainInteractor {
    private lateinit var mainModel: RemoteModel.MainModel

    init {
        getRemoteModel()
    }

    private fun getRemoteModel() {
        val fileName = context.getString(R.string.data_file)
        val jsonString = context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
        mainModel = Gson().fromJson(jsonString, RemoteModel.MainModel::class.java)
    }

    override fun getAllRoutes(): List<RemoteModel.Route> {
        return mainModel.routes
    }

    override fun getRoute(id: Int): RemoteModel.Route {
        return mainModel.routes[id]
    }

    fun getPorivderAttributes(): RemoteModel.ProviderAttributes {
        return mainModel.providerAttributes
    }
}