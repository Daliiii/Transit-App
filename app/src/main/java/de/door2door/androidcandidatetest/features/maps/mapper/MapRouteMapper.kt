package de.door2door.androidcandidatetest.features.maps.mapper

import android.view.View
import de.door2door.androidcandidatetest.R
import de.door2door.androidcandidatetest.features.maps.model.*
import de.door2door.androidcandidatetest.model.RemoteModel
import de.door2door.androidcandidatetest.utils.DateHelper
import de.door2door.androidcandidatetest.utils.Logger
import java.text.DecimalFormat

class MapRouteMapper {
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
        private const val WALKING = "walking"
        private const val CHANGE = "change"
        private const val CYCLING = "cycling"
        private const val DRIVING = "driving"
    }

    fun mapRouteMaps(route: RemoteModel.Route) : RouteMaps {
        val segmentMapsList = ArrayList<SegmentMaps>()
        route.segments.forEach {
            val startLat = it.stops.first().lat
            val startLng = it.stops.first().lng
            val transportMode = it.travelMode
            val polyline = it.polyline
            val color = it.color
            val segmentMaps = SegmentMaps(
                startLat = startLat,
                startLng = startLng,
                transportMode = transportMode,
                polyline = polyline,
                color = color)
            segmentMapsList.add(segmentMaps)
        }
        return RouteMaps(segmentMapsList)
    }

    fun mapRouteDetail(route: RemoteModel.Route, providers: RemoteModel.ProviderAttributes): RouteDetail {
        val transportMode = route.type
        val price = formatPrice(route.price)
        val provider = StringBuilder().append("Provided by ").append(route.provider).toString()
        val providerIcon = getProviderIcon(route.provider, providers)
        val firstStop = route.segments.first().stops.first()
        val lastSegment = route.segments.last()
        val lastStop =
            if (lastSegment.numStops == 0) lastSegment.stops[1] else lastSegment.stops[lastSegment.numStops - 1]
        val duration =
            StringBuilder().append(DateHelper.getDuration(firstStop.datetime, lastStop.datetime)).append(" Min")
                .toString()
        val modeList = ArrayList<Mode>()
        var transportModeText  = ""
        route.segments.forEachIndexed { index, segment ->
            val isLastIndex = index == route.segments.lastIndex
            val visibility = if (isLastIndex) View.VISIBLE else View.GONE
            if (transportMode == PUBLIC_TRANSPORT) {
                transportModeText = PUBLIC_TRANSPORT_TEXT
                when {
                    segment.travelMode == WALKING -> {
                        val durationText = StringBuilder().append(
                            DateHelper.getDuration(
                                segment.stops.first().datetime,
                                segment.stops.last().datetime
                            )
                        ).append(" Min").toString()
                        val sharedMode = SharedMode(
                            departureStop = segment.stops.first().name,
                            departureIcon = segment.iconUrl,
                            departureTime = DateHelper.formatDate(segment.stops.first().datetime),
                            arrivalStop = segment.stops.last().name,
                            arrivalIcon = segment.iconUrl,
                            arrivalTime = DateHelper.formatDate(segment.stops.last().datetime),
                            mode = segment.travelMode,
                            modeIcon = "",
                            color = segment.color,
                            visibility = visibility,
                            description = durationText,
                            externalIcon = R.drawable.icon_oepnv_fussweg
                        )
                        modeList.add(sharedMode)
                    }
                    segment.travelMode == CHANGE -> {
                        val changeMode = ChangeMode(
                            departureStop = "",
                            departureTime = "",
                            departureIcon = "",
                            arrivalIcon = "",
                            arrivalStop = "",
                            arrivalTime = "",
                            color = segment.color,
                            iconUrl = segment.iconUrl,
                            visibility = visibility
                        )
                        modeList.add(changeMode)
                    }
                    else -> {
                        val publicTransportStopList = ArrayList<PublicTransportStop>()
                        segment.stops.forEach {
                            val publicTransportStop = PublicTransportStop(
                                stopName = it.name,
                                stopTime = it.datetime
                            )
                            publicTransportStopList.add(publicTransportStop)
                        }
                        val publicTransportMode = PublicTransportMode(
                            departureStop = segment.stops.first().name,
                            departureIcon = segment.iconUrl,
                            departureTime = DateHelper.formatDate(segment.stops.first().datetime),
                            arrivalStop = segment.stops.last().name,
                            arrivalTime = DateHelper.formatDate(segment.stops.last().datetime),
                            arrivalIcon = segment.iconUrl,
                            direction = segment.description,
                            changeNumber = segment.numStops,
                            stops = publicTransportStopList,
                            color = segment.color,
                            visibility = visibility
                        )
                        modeList.add(publicTransportMode)
                    }
                }
            } else {
                transportModeText = when (transportMode){
                    CAR_SHARING ->  CAR_SHARING_TEXT
                    PRIVATE_BIKE -> PRIVATE_BIKE_TEXT
                    SHARED_BIKE ->  SHARED_BIKE_TEXT
                    else -> TAXI_TEXT
                }
                val travelMode = segment.travelMode
                Logger.d("MapRouteMapper",travelMode)
                val iconDrawable: Int
                val modeIcon: String
                when (travelMode) {
                    WALKING -> {
                        iconDrawable = R.drawable.icon_oepnv_fussweg
                        modeIcon = ""
                    }
                    CYCLING -> {
                        iconDrawable = R.drawable.icon_parken_charging_bicycle
                        modeIcon = ""
                    }
                    DRIVING -> {
                        iconDrawable = R.drawable.icon_menu_right_carsharing
                        modeIcon = ""
                    }
                    else -> {
                        iconDrawable = -1
                        modeIcon = segment.iconUrl
                    }
                }
                val description = if (segment.travelMode == WALKING) StringBuilder().append(
                    DateHelper.getDuration(
                        segment.stops.first().datetime,
                        segment.stops.last().datetime
                    )
                ).append(" Min").toString() else segment.travelMode
                val sharedMode = SharedMode(
                    departureStop = segment.stops.first().name,
                    departureIcon = modeIcon,
                    departureTime = DateHelper.formatDate(segment.stops.first().datetime),
                    arrivalStop = segment.stops.last().name,
                    arrivalIcon = modeIcon,
                    arrivalTime = DateHelper.formatDate(segment.stops.last().datetime),
                    mode = segment.travelMode,
                    modeIcon = modeIcon,
                    color = segment.color,
                    visibility = visibility,
                    description = description,
                    externalIcon = iconDrawable
                )
                modeList.add(sharedMode)
            }
        }
        return RouteDetail(
            transportMode = transportModeText,
            price = price,
            transportDuration = duration,
            provider = provider,
            providerIcon = providerIcon,
            modes = modeList
        )
    }

    private fun formatPrice(price: RemoteModel.Price?): String {
        if (price == null)
            return ""
        val formatter = DecimalFormat("#,##")
        val formattedPrice = formatter.format(price.amount)
        val stringBuilder = StringBuilder()
        return stringBuilder.append("Price: ").append(formattedPrice).append(" ${price.currency}").toString()
    }

    private fun getProviderIcon(providerName: String, providerAttributes: RemoteModel.ProviderAttributes): String {
        return when (providerName) {
            "vbb" -> providerAttributes.vbb.providerIconUrl
            "drivenow" -> providerAttributes.driveNow.providerIconUrl
            "car2go" -> providerAttributes.car2go.providerIconUrl
            "google" -> providerAttributes.google.providerIconUrl
            "nextbike" -> providerAttributes.nextBike.providerIconUrl
            "callabike" -> providerAttributes.callABike.providerIconUrl
            else -> ""
        }
    }
}