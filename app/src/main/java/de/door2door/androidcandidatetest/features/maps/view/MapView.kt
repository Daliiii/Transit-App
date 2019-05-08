package de.door2door.androidcandidatetest.features.maps.view

import com.google.android.gms.maps.model.LatLng
import de.door2door.androidcandidatetest.base.BaseView
import de.door2door.androidcandidatetest.features.maps.model.ChangeMode
import de.door2door.androidcandidatetest.features.maps.model.PublicTransportMode
import de.door2door.androidcandidatetest.features.maps.model.RouteDetail
import de.door2door.androidcandidatetest.features.maps.model.SharedMode

interface MapView : BaseView {
    fun initDetailLayout(routeDetail: RouteDetail)

    fun createSharedLayout(sharedMode: SharedMode)

    fun createChangeModeLayout(changeMode: ChangeMode)

    fun createPublicTransportLayout(publicTransportMode: PublicTransportMode)

    fun showHideScrollLayout(visibility: Int)

    fun showRecyclerView(visibility: Int, index: Int, iconArrow: Int)

    fun initMaps()

    fun initToolbar()

    fun setUpCurrentAndMoveToStartLocation(lat: Double, lng: Double)

    fun drawPolyline(decode: List<LatLng>, color: String)

    fun showToast()
}