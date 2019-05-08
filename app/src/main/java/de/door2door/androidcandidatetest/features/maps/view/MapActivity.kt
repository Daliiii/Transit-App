package de.door2door.androidcandidatetest.features.maps.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import de.door2door.androidcandidatetest.R
import de.door2door.androidcandidatetest.base.BaseActivity
import de.door2door.androidcandidatetest.features.maps.mapper.MapRouteMapper
import de.door2door.androidcandidatetest.features.maps.model.ChangeMode
import de.door2door.androidcandidatetest.features.maps.model.PublicTransportMode
import de.door2door.androidcandidatetest.features.maps.model.RouteDetail
import de.door2door.androidcandidatetest.features.maps.model.SharedMode
import de.door2door.androidcandidatetest.features.maps.presenter.MapPresenter
import de.door2door.androidcandidatetest.interactor.MainInteractorImp
import de.door2door.androidcandidatetest.utils.SVGLoader
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : BaseActivity<MapPresenter>(), MapView, OnMapReadyCallback {
    private lateinit var inflater: LayoutInflater
    private val recyclerViews = ArrayList<RecyclerView>()
    private val iconArrows = ArrayList<ImageView>()
    private var lastKnownLocation = Location("")
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mMap: GoogleMap

    companion object {
        const val ROUTE_ID = "route_id"
        const val ZOOM = 15f
        const val POLY_WIDTH = 12f
    }

    override fun showToast() {
        Toast.makeText(
            this,
            this.getString(R.string.requestPermission),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val id = intent.getIntExtra(ROUTE_ID, -1)
        inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        presenter.setRouteId(id)
        presenter.onViewCreated()
    }

    override fun showRecyclerView(visibility: Int, index: Int, iconArrow: Int) {
        recyclerViews[index].visibility = visibility
        iconArrows[index].setImageDrawable(getDrawable(iconArrow))
    }

    override fun showHideScrollLayout(visibility: Int) {
        if (scrollLayout.visibility == View.VISIBLE) {
            val scrollHeight = scrollLayout.measuredHeight
            val initialWrapperY = cardView.y
            val oa = ObjectAnimator.ofFloat(cardView, "y", initialWrapperY, initialWrapperY + scrollHeight)
            oa.duration = 400
            oa.start()
            oa.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    scrollLayout.visibility = View.INVISIBLE
                }
            })
        } else {
            val initialWrapperY = cardView.y
            scrollLayout.visibility = View.VISIBLE
            cardView.y = initialWrapperY
            val vto = cardView.viewTreeObserver
            val anon = object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    cardView.viewTreeObserver.removeOnPreDrawListener(this)
                    val footerHeight = scrollLayout.measuredHeight
                    val oa =
                        ObjectAnimator.ofFloat(cardView, "y", initialWrapperY, (initialWrapperY - footerHeight))
                    oa.duration = 400
                    oa.start()
                    return true
                }
            }
            vto.addOnPreDrawListener(anon)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        presenter.onMapReady()
    }

    override fun initToolbar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "Route map"
            actionBar.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initMaps() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun createSharedLayout(sharedMode: SharedMode) {
        val child = inflater.inflate(R.layout.shared_mode, null)
        val modeIcon: ImageView = child.findViewById(R.id.modeIcon)
        val startLocation: TextView = child.findViewById(R.id.startLocation)
        val startTime: TextView = child.findViewById(R.id.startTime)
        val destinationLocation: TextView = child.findViewById(R.id.destinationLocation)
        val destinationTime: TextView = child.findViewById(R.id.destinationTime)
        val arrivalLayout: LinearLayout = child.findViewById(R.id.arrivalLayout)
        val description: TextView = child.findViewById(R.id.description)
        description.text = sharedMode.description
        startLocation.text = sharedMode.departureStop
        startTime.text = sharedMode.departureTime
        destinationLocation.text = sharedMode.arrivalStop
        destinationTime.text = sharedMode.arrivalTime
        arrivalLayout.visibility = sharedMode.visibility
        if (sharedMode.modeIcon == "") modeIcon.setImageDrawable(getDrawable(sharedMode.externalIcon))
        else SVGLoader.fetchSvg(this, sharedMode.modeIcon, modeIcon)
        routeContainer.addView(child)
    }

    override fun createChangeModeLayout(changeMode: ChangeMode) {
        val child = inflater.inflate(R.layout.changes_mode, null)
        val changeIcon: ImageView = child.findViewById(R.id.changeIcon)
        SVGLoader.fetchSvg(this, changeMode.iconUrl, changeIcon)
        routeContainer.addView(child)
    }

    override fun createPublicTransportLayout(publicTransportMode: PublicTransportMode) {
        val child = inflater.inflate(R.layout.public_transport_mode, null)
        val transportIcon: ImageView = child.findViewById(R.id.transportIcon)
        val arrowIcon: ImageView = child.findViewById(R.id.arrowIcon)
        val startStation: TextView = child.findViewById(R.id.startStation)
        val startTime: TextView = child.findViewById(R.id.StartTime)
        val direction: TextView = child.findViewById(R.id.direction)
        val stopsNumber: TextView = child.findViewById(R.id.stopsNumber)
        val stopsNumberLayout: LinearLayout = child.findViewById(R.id.stopsNumberLayout)
        val destinationStation: TextView = child.findViewById(R.id.destinationStation)
        val destinationTime: TextView = child.findViewById(R.id.destinationTime)
        val arrivalLayout: LinearLayout = child.findViewById(R.id.arrivalLayout)
        arrivalLayout.visibility = publicTransportMode.visibility
        val recyclerView: RecyclerView = child.findViewById(R.id.recyclerView)
        recyclerViews.add(recyclerView)
        iconArrows.add(arrowIcon)
        val adapter = StopAdapter(this, publicTransportMode.stops!!)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        SVGLoader.fetchSvg(this, publicTransportMode.arrivalIcon, transportIcon)
        startStation.text = publicTransportMode.departureStop
        startTime.text = publicTransportMode.departureTime
        direction.text = publicTransportMode.direction
        direction.text = publicTransportMode.direction
        stopsNumber.text = StringBuilder().append(publicTransportMode.changeNumber).append(" Stops")
        destinationStation.text = publicTransportMode.arrivalStop
        destinationTime.text = publicTransportMode.arrivalTime
        destinationTime.text = publicTransportMode.arrivalTime
        stopsNumberLayout.setOnClickListener {
            presenter.onStopNumberClicked(recyclerView.visibility, recyclerViews.indexOf(recyclerView))
        }
        routeContainer.addView(child)
    }

    override fun initDetailLayout(routeDetail: RouteDetail) {
        headerView.setOnClickListener {
            presenter.headerViewClicked(scrollLayout.visibility)
        }
        transportType.text = routeDetail.transportMode
        duration.text = routeDetail.transportDuration
        routePrice.text = routeDetail.price
        provider.text = routeDetail.provider
        SVGLoader.fetchSvg(this, routeDetail.providerIcon, providerIcon)
        presenter.onDetailInited()
    }

    override fun instantiatePresenter(): MapPresenter {
        return MapPresenter(this, MapRouteMapper(), MainInteractorImp(this))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MapPresenter.PERMISSIONS_REQUEST_ENABLE_LOCATION -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    presenter.locationPermissionRefused()
                } else {
                    presenter.locationPermissionGranted()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun setUpCurrentAndMoveToStartLocation(lat: Double, lng: Double) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.lastLocation
            .addOnSuccessListener(this) { location ->
                if (location != null) {
                    lastKnownLocation = location
                    mMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                lat,
                                lng
                            ), ZOOM
                        )
                    )
                }
            }
        mMap.isMyLocationEnabled = true
        presenter.currentLocationReady()
    }

    override fun drawPolyline(decode: List<LatLng>, color: String) {
        mMap.addPolyline(
            PolylineOptions()
                .addAll(decode)
                .width(POLY_WIDTH)
                .color(Color.parseColor(color))
                .geodesic(true)
        )
    }
}
