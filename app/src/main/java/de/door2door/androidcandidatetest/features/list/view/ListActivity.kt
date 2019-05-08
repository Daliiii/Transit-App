package de.door2door.androidcandidatetest.features.list.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import de.door2door.androidcandidatetest.R
import de.door2door.androidcandidatetest.base.BaseActivity
import de.door2door.androidcandidatetest.features.list.mapper.RouteModelMapper
import de.door2door.androidcandidatetest.features.list.model.RouteModel
import de.door2door.androidcandidatetest.features.list.presenter.ListPresenter
import de.door2door.androidcandidatetest.interactor.MainInteractorImp
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : BaseActivity<ListPresenter>(), ListView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        presenter.onViewCreated()
    }

    override fun instantiatePresenter(): ListPresenter {
        return ListPresenter(this, RouteModelMapper(), MainInteractorImp(this))
    }

    override fun initRecyclerView(routeModels: ArrayList<RouteModel>) {
        val adapter = RouteListAdapter(this, routeModels)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun initToolbar() {
        collapsingToolbar.isTitleEnabled = false
        toolbar.title = getString(R.string.app_name)
    }
}
