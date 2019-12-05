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

/*
    MainActivity controls the functionality of the parking lot list screen

 */
class MainActivity : AppCompatActivity(), ParkingLotListener, AnkoLogger {

    var parkingLots = ArrayList<ParkingLotModel>()
    lateinit var app: MainApp

    /*called when parking lot is clicked, starts appropriate parking lot activity
     */
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

    /*
    First method called when main activity starts. Sets the app to the correct view.
    assigns all of the needed elements for the activity.
     */
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
            /*
            Called when the back arrow in the toolbar is pressed. Starts the home activity
             */
            override fun onClick(v: View) {
                startActivity(intentFor<HomeActivity>())
                finish()
            }
        })
    }

    /*
    Loads the parking lots by getting the data from the spotData object. Then calling showParkinglots
    to actually place the parking lots in the list on screen.
     */
    private fun loadParkingLots(){
        displayData(app.spotData)
        //dumydata()
        //parkingLots.sortByDescending { it.id }
        showParkingLots(parkingLots)
    }

    /*
    Uses the ParkingLotAdapter to implement parking lot objects into the list on screen.
     */
    fun showParkingLots(parkinglots: List<ParkingLotModel>){
        recyclerView.adapter = ParkingLotAdapter(parkinglots, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    /*
    Populates the data for parking lot one. Calls calculateSpots to fine the number of spots
     */
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

    /*
    Calculates the number of taken spots in the parking lot using the data from the server.
     */
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

    /*
    Used for populating the data for parking lot 2 (Fake parking lot used to show the possibility of
    multiple lots.
     */
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
