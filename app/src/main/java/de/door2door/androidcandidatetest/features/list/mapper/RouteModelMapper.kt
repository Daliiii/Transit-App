package de.door2door.androidcandidatetest.features.list.mapper

import de.door2door.androidcandidatetest.features.list.model.RouteModel
import de.door2door.androidcandidatetest.model.RemoteModel
import de.door2door.androidcandidatetest.utils.DateHelper

class RouteModelMapper {
    companion object {
        private const val PUBLIC_TRANSPORT = "public_transport"
        private const val CAR_SHARING = "car_sharing"
        private const val PRIVATE_BIKE = "private_bike"
        private const val SHARED_BIKE = "bike_sharing"
        private const val PUBLIC_TRANSPORT_TEXT = "Public transport"
        private const val CAR_SHARING_TEXT = "Car sharing"
        private const val PRIVATE_BIKE_TEXT = "Private bike"
        private const val SHARED_BIKE_TEXT = "Bike sharing "
        private const val TAXI_TEXT = "Taxi "
    }

    fun mapRouteModel(route: RemoteModel.Route): RouteModel {
        val changesCount = route.segments.size - 1
        val firstStop = route.segments.first().stops.first()
        val departureStop = firstStop.name
        val lastSegment = route.segments.last()
        val lastStop = if (lastSegment.numStops == 0) lastSegment.stops[1] else lastSegment.stops.last()
        val arrivalStop = lastStop.name
        val departureTime = DateHelper.formatDate(firstStop.datetime)
        val arrivalTime = DateHelper.formatDate(lastStop.datetime)
        val duration = DateHelper.getDuration(firstStop.datetime, lastStop.datetime)
        val transportType = when (route.type) {
            PUBLIC_TRANSPORT -> PUBLIC_TRANSPORT_TEXT
            CAR_SHARING -> CAR_SHARING_TEXT
            PRIVATE_BIKE -> PRIVATE_BIKE_TEXT
            SHARED_BIKE -> SHARED_BIKE_TEXT
            else -> TAXI_TEXT
        }
        return RouteModel(
            departureStop= departureStop,
            arrivalStop = arrivalStop,
            departureTime = departureTime,
            arrivalTime = arrivalTime,
            duration = duration,
            changeNumber =  changesCount,
            transportType = transportType)
    }
}