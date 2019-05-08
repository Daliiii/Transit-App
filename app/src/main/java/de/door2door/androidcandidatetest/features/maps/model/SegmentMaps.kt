package de.door2door.androidcandidatetest.features.maps.model

data class SegmentMaps(
    val startLat: Double,
    val startLng: Double,
    val transportMode: String,
    val polyline: String?,
    val color : String
)