package com.example.centralparkingtracker

import android.app.Application
import model.SpotData


/*
    MainApp is used for creating an easily accessible object.
 */
class MainApp: Application() {

    lateinit var spotData: SpotData

    override fun onCreate() {
        super.onCreate()


    }
}