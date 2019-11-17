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
import org.jetbrains.anko.startActivityForResult
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View


class MainActivity : AppCompatActivity(), ParkingLotListener {

    var parkingLots = ArrayList<ParkingLotModel>()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        setSupportActionBar(toolbarAdd)
        toolbarAdd.title = "PT at CMU"

        loadParkingLots()
        toolbarAdd.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                startActivity(intentFor<HomeActivity>())
                finish()
            }
        })
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
                parkingLot.latitude = 43.586303
                parkingLot.longitude = -84.775202
            }
            else{
                parkingLot.title = "Parking Lot #${i}"
                parkingLot.info = "This lot accepts passes 1, 2, 3."
                parkingLot.spotTotal = 8
                parkingLot.spotsTaken = 5
                parkingLot.id = i
                parkingLot.latitude = 43.586303
                parkingLot.longitude = -84.775202

            }
            parkingLots.add(parkingLot.copy())
        }
    }

}
