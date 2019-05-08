package de.door2door.androidcandidatetest.features.maps.presenter

import android.view.View
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import de.door2door.androidcandidatetest.R
import de.door2door.androidcandidatetest.features.maps.mapper.MapRouteMapper
import de.door2door.androidcandidatetest.features.maps.model.*
import de.door2door.androidcandidatetest.features.maps.view.MapView
import de.door2door.androidcandidatetest.interactor.MainInteractorImp
import de.door2door.androidcandidatetest.model.RemoteModel
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MapPresenterTest {
    @Mock
    private lateinit var mapper: MapRouteMapper
    @Mock
    private lateinit var mainInteractor: MainInteractorImp
    @Mock
    private lateinit var mapView: MapView

    private lateinit var mapPresenter: MapPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mapPresenter = MapPresenter(mapView, mapper, mainInteractor)
    }

    @Test
    fun `should init detail layout`() {
        val routeDetail = Mockito.mock(RouteDetail::class.java)
        val route = Mockito.mock(RemoteModel.Route::class.java)
        val providerAttributes = Mockito.mock(RemoteModel.ProviderAttributes::class.java)
        whenever(mapper.mapRouteDetail(any(), any())).thenReturn(routeDetail)
        whenever(mainInteractor.getRoute(any())).thenReturn(route)
        whenever(mainInteractor.getPorivderAttributes()).thenReturn(providerAttributes)

        mapPresenter.onViewCreated()

        verify(mapView).initDetailLayout(any())
    }

    @Test
    fun `should create SharedLayout`() {
        val routeDetail = Mockito.mock(RouteDetail::class.java)
        val route = Mockito.mock(RemoteModel.Route::class.java)
        val providerAttributes = Mockito.mock(RemoteModel.ProviderAttributes::class.java)
        whenever(mapper.mapRouteDetail(any(), any())).thenReturn(routeDetail)
        whenever(mainInteractor.getRoute(any())).thenReturn(route)
        whenever(mainInteractor.getPorivderAttributes()).thenReturn(providerAttributes)
        val mode = Mockito.mock(SharedMode::class.java)
        val modes = arrayListOf<Mode>(mode)
        whenever(routeDetail.modes).thenReturn(modes)
        mapPresenter.onViewCreated()

        mapPresenter.onDetailInited()

        verify(mapView).createSharedLayout(any())
    }

    @Test
    fun `should create ChangeMode`() {
        val routeDetail = Mockito.mock(RouteDetail::class.java)
        val route = Mockito.mock(RemoteModel.Route::class.java)
        val providerAttributes = Mockito.mock(RemoteModel.ProviderAttributes::class.java)
        whenever(mapper.mapRouteDetail(any(), any())).thenReturn(routeDetail)
        whenever(mainInteractor.getRoute(any())).thenReturn(route)
        whenever(mainInteractor.getPorivderAttributes()).thenReturn(providerAttributes)
        val mode = Mockito.mock(SharedMode::class.java)
        val modes = arrayListOf<Mode>(mode)
        whenever(routeDetail.modes).thenReturn(modes)
        mapPresenter.onViewCreated()

        mapPresenter.onDetailInited()

        verify(mapView).createSharedLayout(any())
    }

    @Test
    fun `should create ChangeModelLayout`() {
        val routeDetail = Mockito.mock(RouteDetail::class.java)
        val route = Mockito.mock(RemoteModel.Route::class.java)
        val providerAttributes = Mockito.mock(RemoteModel.ProviderAttributes::class.java)
        whenever(mapper.mapRouteDetail(any(), any())).thenReturn(routeDetail)
        whenever(mainInteractor.getRoute(any())).thenReturn(route)
        whenever(mainInteractor.getPorivderAttributes()).thenReturn(providerAttributes)
        val mode = Mockito.mock(ChangeMode::class.java)
        val modes = arrayListOf<Mode>(mode)
        whenever(routeDetail.modes).thenReturn(modes)
        mapPresenter.onViewCreated()

        mapPresenter.onDetailInited()

        verify(mapView).createChangeModeLayout(any())
    }

    @Test
    fun `should create PublicTransportLayout`() {
        val routeDetail = Mockito.mock(RouteDetail::class.java)
        val route = Mockito.mock(RemoteModel.Route::class.java)
        val providerAttributes = Mockito.mock(RemoteModel.ProviderAttributes::class.java)
        whenever(mapper.mapRouteDetail(any(), any())).thenReturn(routeDetail)
        whenever(mainInteractor.getRoute(any())).thenReturn(route)
        whenever(mainInteractor.getPorivderAttributes()).thenReturn(providerAttributes)
        val mode = Mockito.mock(PublicTransportMode::class.java)
        val modes = arrayListOf<Mode>(mode)
        whenever(routeDetail.modes).thenReturn(modes)
        mapPresenter.onViewCreated()

        mapPresenter.onDetailInited()

        verify(mapView).createPublicTransportLayout(any())
    }

    @Test
    fun `should hide ScrollView`() {
        mapPresenter.headerViewClicked(any())

        verify(mapView).showHideScrollLayout(any())
    }

    @Test
    fun `should change visibility to gone`() {
        mapPresenter.onStopNumberClicked(View.VISIBLE, 0)

        verify(mapView).showRecyclerView(View.GONE, 0, R.drawable.icon_arrow_right)
    }

    @Test
    fun `should change visibility to visible`() {
        mapPresenter.onStopNumberClicked(View.GONE, 0)

        verify(mapView).showRecyclerView(View.VISIBLE, 0, R.drawable.ic_arrow_up)
    }

    @Test
    fun `should init map`() {
        mapPresenter.locationPermissionGranted()

        verify(mapView).initMaps()
    }

    @Test
    fun `should move to start location`() {
        val routeMaps = Mockito.mock(RouteMaps::class.java)
        val route = Mockito.mock(RemoteModel.Route::class.java)
        val providerAttributes = Mockito.mock(RemoteModel.ProviderAttributes::class.java)
        val routeDetail = Mockito.mock(RouteDetail::class.java)
        val segmentMaps = Mockito.mock(SegmentMaps::class.java)
        whenever(mainInteractor.getRoute(any())).thenReturn(route)
        whenever(mainInteractor.getPorivderAttributes()).thenReturn(providerAttributes)
        whenever(mapper.mapRouteMaps(any())).thenReturn(routeMaps)
        whenever(mapper.mapRouteDetail(any(), any())).thenReturn(routeDetail)
        whenever(routeMaps.segments).thenReturn(mutableListOf(segmentMaps))
        whenever(segmentMaps.startLat).thenReturn(0.0)
        whenever(segmentMaps.startLng).thenReturn(0.0)

        mapPresenter.onViewCreated()
        mapPresenter.onMapReady()

        verify(mapView).setUpCurrentAndMoveToStartLocation(any(), any())
    }
}