package com.example.centralparkingtracker

import model.ParkingLotModel

interface ParkingLotListener {

    fun onParkingLotClick(parkingLot: ParkingLotModel)
}