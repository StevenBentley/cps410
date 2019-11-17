package model


data class ParkingLotModel(
    var title: String = "",
    var info: String = "",
    var spotTotal: Int = 0,
    var spotsTaken: Int = 0,
    var id : Int = 0
)