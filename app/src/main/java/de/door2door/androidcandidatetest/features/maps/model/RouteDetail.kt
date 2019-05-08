package de.door2door.androidcandidatetest.features.maps.model

data class RouteDetail(
    val transportMode: String,
    val transportDuration: String,
    val price: String,
    val provider: String,
    val providerIcon: String,
    val modes: List<Mode>
)