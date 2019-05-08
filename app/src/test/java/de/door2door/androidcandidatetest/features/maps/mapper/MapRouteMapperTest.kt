package de.door2door.androidcandidatetest.features.maps.mapper

import android.view.View
import de.door2door.androidcandidatetest.model.RemoteModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class MapRouteMapperTest {
    private lateinit var mapRouteMapper: MapRouteMapper

    @Before
    fun setUp() {
        mapRouteMapper = MapRouteMapper()
    }

    @Test
    fun `should map Route into Route detail`() {
        val routeRemote = createRouteRemoteData()
        val providerAttributes = createProviderAttributes()
        val routeDetail = mapRouteMapper.mapRouteDetail(routeRemote, providerAttributes)

        assertThat(routeDetail.price).isEqualTo("Price: 2,70 EUR")
        assertThat(routeDetail.provider).isEqualTo("Provided by vbb")
        assertThat(routeDetail.providerIcon).isEqualTo("https://d3m2tfu2xpiope.cloudfront.net/providers/vbb.svg")
        assertThat(routeDetail.transportMode).isEqualTo("Public transport")
        assertThat(routeDetail.transportDuration).isEqualTo("21 Min")
        assertThat(routeDetail.modes.first().visibility).isEqualTo(View.GONE)
        assertThat(routeDetail.modes.first().departureStop).isEqualTo(null)
        assertThat(routeDetail.modes.first().departureTime).isEqualTo("13:30")
        assertThat(routeDetail.modes.last().arrivalStop).isEqualTo("S+U Potsdamer Platz")
        assertThat(routeDetail.modes.first().arrivalTime).isEqualTo("13:38")
    }

    @Test
    fun `should map Route into Route Map`() {
        val routeRemote = createRouteRemoteData()
        val routeMap = mapRouteMapper.mapRouteMaps(routeRemote)

        assertThat(routeMap.segments.first().color).isEqualTo("#b1becc")
        assertThat(routeMap.segments.first().polyline).isEqualTo("uvr_I{yxpABuAFcAp@yHvAwNr@iGPwAh@a@jAg@")
        assertThat(routeMap.segments.first().startLng).isEqualTo(13.403356)
        assertThat(routeMap.segments.first().startLat).isEqualTo(52.530227)
        assertThat(routeMap.segments.first().transportMode).isEqualTo("walking")
        assertThat(routeMap.segments.last().color).isEqualTo("#d64820")
        assertThat(routeMap.segments.last().polyline).isEqualTo("elr_I_fzpAfe@_Sf]dFr_@~UjCbg@yKvj@lFfb@`C|c@hNjc@")
        assertThat(routeMap.segments.last().startLng).isEqualTo(13.410404)
        assertThat(routeMap.segments.last().startLat).isEqualTo(52.528187)
        assertThat(routeMap.segments.last().transportMode).isEqualTo("subway")
    }

    private fun createProviderAttributes(): RemoteModel.ProviderAttributes {
        val vbb = RemoteModel.Vbb(
            providerIconUrl = "https://d3m2tfu2xpiope.cloudfront.net/providers/vbb.svg",
            disclaimer = "Our data is as live and real-time as possible"
        )
        val google = RemoteModel.Google(
            providerIconUrl = "https://d3m2tfu2xpiope.cloudfront.net/providers/google.svg",
            disclaimer = "Our data is as live and real-time as possible."
        )
        val car2go = RemoteModel.Car2go(
            providerIconUrl = "",
            disclaimer = "",
            iosItunesUrl = "",
            iosAppUrl = "",
            androidPackageName = "",
            displayName = ""
        )
        val driveNow = RemoteModel.DriveNow(
            providerIconUrl = "",
            disclaimer = "",
            androidPackageName = "",
            displayName = "",
            ios_app_url = "",
            iosItunesUrl = ""
        )
        val callABike = RemoteModel.CallABike(
            providerIconUrl = "",
            iosItunesUrl = "",
            displayName = "",
            androidPackageName = "",
            disclaimer = "",
            iosAppUrl = ""
        )
        val nextBike = RemoteModel.NextBike(
            providerIconUrl = "",
            iosAppUrl = "",
            disclaimer = "",
            iosItunesUrl = "",
            android_package_name = "",
            display_name = ""
        )
        return RemoteModel.ProviderAttributes(
            vbb,
            driveNow,
            car2go,
            google,
            nextBike,
            callABike
        )
    }

    private fun createRouteRemoteData(): RemoteModel.Route {
        val type = "public_transport"
        val provider = "vbb"
        val firstStop = RemoteModel.Stops(
            lat = 52.530227,
            lng = 13.403356,
            datetime = "2015-04-17T13:30:00+02:00",
            name = null,
            properties = null
        )
        val secondStop = RemoteModel.Stops(
            lat = 52.528187,
            lng = 13.410404,
            datetime = "2015-04-17T13:38:00+02:00",
            name = "U Rosa-Luxemburg-Platz",
            properties = null
        )
        val firstStops = mutableListOf<RemoteModel.Stops>()
        firstStops.add(firstStop)
        firstStops.add(secondStop)
        val firstSegment = RemoteModel.Segments(
            name = null,
            numStops = 0,
            travelMode = "walking",
            polyline = "uvr_I{yxpABuAFcAp@yHvAwNr@iGPwAh@a@jAg@",
            iconUrl = "https://d3m2tfu2xpiope.cloudfront.net/vehicles/walking.svg",
            color = "#b1becc",
            description = null,
            stops = firstStops
        )
        val lastFirstStop = RemoteModel.Stops(
            lat = 52.528187,
            lng = 13.410404,
            datetime = "2015-04-17T13:38:00+02:00",
            name = "U Rosa-Luxemburg-Platz",
            properties = null

        )
        val lastLastStop = RemoteModel.Stops(
            lat = 52.509067,
            lng = 13.37798,
            datetime = "2015-04-17T13:51:00+02:00",
            name = "S+U Potsdamer Platz",
            properties = null

        )
        val lastStops = mutableListOf<RemoteModel.Stops>()
        lastStops.add(lastFirstStop)
        lastStops.add(lastLastStop)
        val lastSegment = RemoteModel.Segments(
            name = "U2",
            numStops = 0,
            travelMode = "subway",
            polyline = "elr_I_fzpAfe@_Sf]dFr_@~UjCbg@yKvj@lFfb@`C|c@hNjc@",
            iconUrl = "https://d3m2tfu2xpiope.cloudfront.net/vehicles/subway.svg",
            color = "#d64820",
            description = "S+U Potsdamer Platz",
            stops = lastStops
        )
        val properties = null
        val price = RemoteModel.Price(currency = "EUR", amount = 270)
        val segments = mutableListOf<RemoteModel.Segments>()
        segments.add(firstSegment)
        segments.add(lastSegment)
        return RemoteModel.Route(
            type = type,
            provider = provider,
            segments = segments,
            properties = properties,
            price = price
        )
    }
}