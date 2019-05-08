package de.door2door.androidcandidatetest.features.list.mapper

import de.door2door.androidcandidatetest.model.RemoteModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class RouteModelMapperTest {
    private lateinit var routeModelMapper: RouteModelMapper

    @Before
    fun setUp() {
        routeModelMapper = RouteModelMapper()
    }

    @Test
    fun `should map Route into Route model`() {
        val routeRemote = createRouteRemoteData()

        val routeModel = routeModelMapper.mapRouteModel(routeRemote)

        assertThat(routeModel.changeNumber).isEqualTo(1)
        assertThat(routeModel.transportType).isEqualTo("Public transport")
        assertThat(routeModel.duration).isEqualTo(21L)
        assertThat(routeModel.departureTime).isEqualTo("13:30")
        assertThat(routeModel.departureStop).isEqualTo(null)
        assertThat(routeModel.arrivalStop).isEqualTo("S+U Potsdamer Platz")
        assertThat(routeModel.arrivalTime).isEqualTo("13:51")
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
            numStops = 8,
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