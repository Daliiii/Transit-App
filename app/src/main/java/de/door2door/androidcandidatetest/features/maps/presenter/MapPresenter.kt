package de.door2door.androidcandidatetest.features.maps.presenter

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import com.google.maps.android.PolyUtil
import de.door2door.androidcandidatetest.R
import de.door2door.androidcandidatetest.base.BasePresenter
import de.door2door.androidcandidatetest.features.maps.mapper.MapRouteMapper
import de.door2door.androidcandidatetest.features.maps.model.*
import de.door2door.androidcandidatetest.features.maps.view.MapActivity
import de.door2door.androidcandidatetest.features.maps.view.MapView
import de.door2door.androidcandidatetest.interactor.MainInteractorImp


class MapPresenter(
    mapView: MapView,
    private val mapper: MapRouteMapper,
    private val interactor: MainInteractorImp
    ) : BasePresenter<MapView>(mapView) {
    private var routeId: Int = -1
    private lateinit var routeDetail: RouteDetail
    private lateinit var routeMaps: RouteMaps
    companion object {
        const val PERMISSIONS_REQUEST_ENABLE_LOCATION = 96
    }

    fun setRouteId(routeId: Int) {
        this.routeId = routeId
    }

    override fun onViewCreated() {
        routeDetail = mapper.mapRouteDetail(interactor.getRoute(routeId), interactor.getPorivderAttributes())
        routeMaps = mapper.mapRouteMaps(interactor.getRoute(routeId))
        checkPermission()
        view.initToolbar()
        view.initDetailLayout(routeDetail)
    }

    fun onDetailInited() {
        routeDetail.modes.forEach {
            when (it) {
                is SharedMode -> {
                    view.createSharedLayout(it)
                }
                is ChangeMode -> {
                    view.createChangeModeLayout(it)
                }
                is PublicTransportMode -> {
                    view.createPublicTransportLayout(it)
                }
            }
        }
    }

    fun headerViewClicked(visibility: Int) {
        view.showHideScrollLayout(visibility)
    }

    fun onStopNumberClicked(visibility: Int, index: Int) {
        val visibilityInversed: Int
        val arrowIcon: Int
        if (visibility == View.VISIBLE) {
            visibilityInversed = View.GONE
            arrowIcon = R.drawable.icon_arrow_right
        } else {
            visibilityInversed = View.VISIBLE
            arrowIcon = R.drawable.ic_arrow_up
        }
        view.showRecyclerView(visibilityInversed, index, arrowIcon)
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view.getContext().checkSelfPermission(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                && view.getContext().checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                if ((view.getContext() as MapActivity).shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION) ||
                    (view.getContext() as MapActivity).shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                ) {
                    view.showToast()
                    requestPermission()
                } else {
                    requestPermission()
                }
            } else {
                view.initMaps()
            }
        } else {
            view.initMaps()
        }
    }

    @SuppressLint("NewApi")
    fun requestPermission() {
        (view.getContext() as MapActivity).requestPermissions(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PERMISSIONS_REQUEST_ENABLE_LOCATION
        )
    }

    fun locationPermissionGranted() {
        view.initMaps()
    }

    fun locationPermissionRefused() {
        view.showToast()
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", view.getContext().packageName, null)
        intent.data = uri
        view.getContext().startActivity(intent)
    }

    fun onMapReady() {
        view.setUpCurrentAndMoveToStartLocation(routeMaps.segments.first().startLat, routeMaps.segments.first().startLng)
    }

    fun currentLocationReady() {
        routeMaps.segments.forEach {
            it.polyline?.let { poly ->
                view.drawPolyline(PolyUtil.decode(poly), it.color)
            }
        }
    }
}