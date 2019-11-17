package com.example.centralparkingtracker

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.parking_lot_card.*
import model.ParkingLotModel
import com.example.centralparkingtracker.ParkingLotActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), ParkingLotListener {
    override fun onParkingLotClick(parkingLot: ParkingLotModel) {
           if(parkingLot.spotTotal == 6){
               startActivity(intentFor<ParkingLotActivity>())
               finish()
           }
        else {
               startActivity(intentFor<ParkingLotActivity2>())
               finish()
           }

    }

    var parkingLots = ArrayList<ParkingLotModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        setSupportActionBar(toolbarAdd)
        toolbarAdd.title = "Parking Tracker"
        loadParkingLots()

    }


    private fun loadParkingLots(){
        dumydata()
        showParkingLots(parkingLots)
    }

    fun showParkingLots(parkinglots: List<ParkingLotModel>){
        recyclerView.adapter = ParkingLotAdapter(parkinglots, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun dumydata(){
        var parkingLot = ParkingLotModel()

        for(i in 1..2){
            if(i == 1) {
                parkingLot.title = "Parking Lot #${i}"
                parkingLot.info = "This lot accepts passes 1, 2, 3."
                parkingLot.spotTotal = 6
                parkingLot.spotsTaken = 4
                parkingLot.id = i
            }
            else{
                parkingLot.title = "Parking Lot #${i}"
                parkingLot.info = "This lot accepts passes 1, 2, 3."
                parkingLot.spotTotal = 8
                parkingLot.spotsTaken = 5
                parkingLot.id = i
            }
            parkingLots.add(parkingLot.copy())
        }
    }

}
