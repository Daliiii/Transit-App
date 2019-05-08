package de.door2door.androidcandidatetest.model

import com.google.gson.annotations.SerializedName

object RemoteModel {
    data class MainModel (
        @SerializedName("routes") val routes : List<Route>,
        @SerializedName("provider_attributes") val providerAttributes : ProviderAttributes
    )

    data class Route (
        @SerializedName("type") val type : String = "",
        @SerializedName("provider") val provider : String = "",
        @SerializedName("segments") val segments : List<Segments>,
        @SerializedName("properties") val properties : Properties?,
        @SerializedName("price") val price : Price?
    )

    data class Properties(
        @SerializedName("address") val address : String= "",
        @SerializedName("model") val model : String= "",
        @SerializedName("license_plate") val licensePlate : String= "",
        @SerializedName("fuel_level") val fuelLevel : String= "",
        @SerializedName("engine_type") val engineType : String= "",
        @SerializedName("internal_cleanliness") val internalCleanliness : String= "",
        @SerializedName("description") val description : String= "",
        @SerializedName("seats") val seats : String?,
        @SerializedName("doors") val doors : String?

    )

    data class Price (
        @SerializedName("currency") val currency : String= "",
        @SerializedName("amount") val amount : Int
    )

    data class Segments (
        @SerializedName("name") val name : String?,
        @SerializedName("num_stops") val numStops : Int = 0,
        @SerializedName("stops") val stops : List<Stops>,
        @SerializedName("travel_mode") val travelMode : String = "",
        @SerializedName("description") val description : String?,
        @SerializedName("color") val color : String = "",
        @SerializedName("icon_url") val iconUrl : String = "",
        @SerializedName("polyline") val polyline : String = ""
    )

    data class Stops (
        @SerializedName("lat") val lat : Double = 0.0,
        @SerializedName("lng") val lng : Double = 0.0,
        @SerializedName("datetime") val datetime : String = "" ,
        @SerializedName("name") val name : String? = "",
        @SerializedName("properties") val properties : String?
    )

    data class ProviderAttributes (
        @SerializedName("vbb") val vbb : Vbb,
        @SerializedName("drivenow") val driveNow : DriveNow,
        @SerializedName("car2go") val car2go : Car2go,
        @SerializedName("google") val google : Google,
        @SerializedName("nextbike") val nextBike : NextBike,
        @SerializedName("callabike") val callABike : CallABike
    )

    data class Vbb (
        @SerializedName("provider_icon_url") val providerIconUrl : String = "",
        @SerializedName("disclaimer") val disclaimer : String = ""
    )

    data class Car2go (
        @SerializedName("provider_icon_url") val providerIconUrl : String = "",
        @SerializedName("disclaimer") val disclaimer : String= "",
        @SerializedName("ios_itunes_url") val iosItunesUrl : String= "",
        @SerializedName("ios_app_url") val iosAppUrl : String = "",
        @SerializedName("android_package_name") val androidPackageName : String = "",
        @SerializedName("display_name") val displayName : String = ""
    )

    data class Google (
        @SerializedName("provider_icon_url") val providerIconUrl : String= "",
        @SerializedName("disclaimer") val disclaimer : String = ""
    )

    data class DriveNow (
        @SerializedName("provider_icon_url") val providerIconUrl : String = "",
        @SerializedName("disclaimer") val disclaimer : String = "",
        @SerializedName("ios_itunes_url") val iosItunesUrl : String = "",
        @SerializedName("ios_app_url") val ios_app_url : String = "",
        @SerializedName("android_package_name") val androidPackageName : String = "",
        @SerializedName("display_name") val displayName : String = ""
    )

    data class CallABike (
        @SerializedName("provider_icon_url") val providerIconUrl : String = "",
        @SerializedName("disclaimer") val disclaimer : String = "",
        @SerializedName("ios_itunes_url") val iosItunesUrl : String = "",
        @SerializedName("ios_app_url") val iosAppUrl : String = "",
        @SerializedName("android_package_name") val androidPackageName : String = "",
        @SerializedName("display_name") val displayName : String = ""
    )

    data class NextBike (
        @SerializedName("provider_icon_url") val providerIconUrl : String = "",
        @SerializedName("disclaimer") val disclaimer : String = "",
        @SerializedName("ios_itunes_url") val iosItunesUrl : String = "",
        @SerializedName("ios_app_url") val iosAppUrl : String = "",
        @SerializedName("android_package_name") val android_package_name : String = "",
        @SerializedName("display_name") val display_name : String = ""
    )
}