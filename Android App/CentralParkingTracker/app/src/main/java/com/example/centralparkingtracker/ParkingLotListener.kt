package com.example.centralparkingtracker

import model.ParkingLotModel

/*
listener interface used for when the user clicks on a parking lot in the list. 
 */
interface ParkingLotListener {

    fun onParkingLotClick(parkingLot: ParkingLotModel)
}