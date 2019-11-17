package com.example.centralparkingtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.home_activity.*

import model.LocationModel
import model.ParkingLotModel
import org.jetbrains.anko.intentFor

class HomeActivity : AppCompatActivity(), AreaListener {

    var locations = ArrayList<LocationModel>()

    override fun onAreaClick(location: LocationModel) {
        if(location.lotNum == 2){
            startActivity(intentFor<MainActivity>())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        setSupportActionBar(toolbarAdd)
        toolbarAdd.title = "Parking Tracker"
        loadAreas()

    }


    private fun loadAreas(){
        dummydata()
        showAreas(locations)
    }

    fun showAreas(locations: List<LocationModel>){
        recyclerView.adapter = LocationAdapter(locations, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun dummydata(){
        var area = LocationModel()

        area.lotNum = 2
        area.title = "Central Michigan University"

        locations.add(area.copy())
    }

}
