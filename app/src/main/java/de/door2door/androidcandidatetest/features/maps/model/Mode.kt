package de.door2door.androidcandidatetest.features.maps.model

interface Mode {
    val departureStop: String?
    val departureIcon: String
    val departureTime: String
    val arrivalStop: String?
    val arrivalTime: String
    val arrivalIcon: String
    val color: String
    val visibility: Int
}