package de.door2door.androidcandidatetest.features.list.model

data class RouteModel(
    val departureStop: String?,
    val arrivalStop: String?,
    val departureTime: String,
    val arrivalTime: String,
    val duration: Long,
    val changeNumber: Int,
    val transportType : String
)