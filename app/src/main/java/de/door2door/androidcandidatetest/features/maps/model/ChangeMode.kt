package de.door2door.androidcandidatetest.features.maps.model

data class ChangeMode(
    override val departureStop: String,
    override val departureIcon: String,
    override val departureTime: String,
    override val arrivalStop: String,
    override val arrivalTime: String,
    override val arrivalIcon: String,
    override val color: String,
    override val visibility: Int,
    val iconUrl: String
) : Mode