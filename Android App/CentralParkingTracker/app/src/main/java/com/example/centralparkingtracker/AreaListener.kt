package com.example.centralparkingtracker

import model.LocationModel

interface AreaListener {
    fun onAreaClick(location: LocationModel)
}