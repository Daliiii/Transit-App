package de.door2door.androidcandidatetest.features.maps.model


data class SharedMode(
    override val departureStop: String? = "",
    override val departureIcon: String = "",
    override val departureTime: String = "",
    override val arrivalStop: String? = "",
    override val arrivalTime: String = "",
    override val arrivalIcon: String = "",
    val mode: String = "",
    val modeIcon: String = "",
    val description: String = "",
    val externalIcon: Int = 0,
    override val color: String = "",
    override val visibility: Int
) : Mode