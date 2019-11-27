package com.example.centralparkingtracker

import android.app.Application
import model.SpotData

class MainApp: Application() {

    lateinit var spotData: SpotData

    override fun onCreate() {
        super.onCreate()


    }
}