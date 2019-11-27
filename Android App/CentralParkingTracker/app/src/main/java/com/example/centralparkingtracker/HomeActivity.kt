package com.example.centralparkingtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.home_activity.*

import model.LocationModel
import model.ParkingLotModel
import model.SpotData
import org.jetbrains.anko.*
import java.net.HttpURLConnection
import java.net.URL

class HomeActivity : AppCompatActivity(), AreaListener, AnkoLogger {

    var locations = ArrayList<LocationModel>()
    lateinit var app: MainApp
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
        app = application as MainApp
        getData()
    }


    private fun loadAreas(){
        loaddata()
        showAreas(locations)
    }

    fun showAreas(locations: List<LocationModel>){
        recyclerView.adapter = LocationAdapter(locations, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun loaddata(){
        var area = LocationModel()

        area.lotNum = 2
        area.title = "Central Michigan University"

        locations.add(area.copy())
    }
    private fun getData(){
        doAsync {
            val connection = URL("http://parking-tracker.azurewebsites.net/parking_picture/api/parking_data/1/").openConnection() as HttpURLConnection
            connection.connect()
            val text = connection.inputStream.use{it.reader().use{reader ->reader.readText()}}
//            val jsonText = text

            uiThread {
                toJson(text)

            }
        }

    }

    private fun toJson(text: String) {
        var spot_list = Gson().fromJson(text, SpotData::class.java )
        info(spot_list)
        app.spotData = spot_list

    }

}
