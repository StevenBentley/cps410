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
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import com.google.gson.Gson
import model.SpotData
import org.jetbrains.anko.*
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity(), ParkingLotListener, AnkoLogger {

    var parkingLots = ArrayList<ParkingLotModel>()
    lateinit var app: MainApp
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
        app = application as MainApp
        loadParkingLots()
        toolbarAdd.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                startActivity(intentFor<HomeActivity>())
                finish()
            }
        })
    }

    private fun loadParkingLots(){
        displayData(app.spotData)
        //dumydata()
        //parkingLots.sortByDescending { it.id }
        showParkingLots(parkingLots)
    }

    fun showParkingLots(parkinglots: List<ParkingLotModel>){
        recyclerView.adapter = ParkingLotAdapter(parkinglots, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun displayData(spot_list: SpotData){
        var parkingLot = ParkingLotModel()

        var spotsTaken = calculateSpots(spot_list)

        parkingLot.title = "Parking Lot #1"
        parkingLot.spotTotal = 6
        parkingLot.spotsTaken = spotsTaken
        parkingLot.info = "This lot accepts handicap passes"
        parkingLot.id = 2
        parkingLot.latitude = 43.586303
        parkingLot.longitude = -84.775202

        parkingLots.add(parkingLot.copy())

        dumydata()

    }

    private fun calculateSpots(spot_list: SpotData) : Int{
        var taken: Int = 0
        if(!spot_list.space1)
            taken++
        if(!spot_list.space2)
            taken++
        if(!spot_list.space3)
            taken++
        if(!spot_list.space4)
            taken++
        if(!spot_list.space5)
            taken++
        if(!spot_list.space6)
            taken++

        return taken
    }
    private fun dumydata(){


        var parkingLot = ParkingLotModel()

           parkingLot.title = "Parking Lot #${2}"
           parkingLot.info = "This lot accepts passes 1, 2, 3."
           parkingLot.spotTotal = 8
           parkingLot.spotsTaken = 3
           parkingLot.id = 1
           parkingLot.latitude = 43.586303
           parkingLot.longitude = -84.775202

           parkingLots.add(parkingLot.copy())



    }

}
