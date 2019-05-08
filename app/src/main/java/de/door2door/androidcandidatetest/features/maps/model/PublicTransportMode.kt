package de.door2door.androidcandidatetest.features.maps.model

data class PublicTransportMode(
    override val departureStop: String?,
    override val departureIcon: String,
    override val departureTime: String,
    override val arrivalStop: String?,
    override val arrivalTime: String,
    override val arrivalIcon: String,
    override val color: String,
    val direction: String?,
    val changeNumber: Int,
    val stops: List<PublicTransportStop>?,
    override val visibility: Int
) : Mode