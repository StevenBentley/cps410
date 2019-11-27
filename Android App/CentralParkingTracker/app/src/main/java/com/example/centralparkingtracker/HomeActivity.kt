package com.example.centralparkingtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.home_activity.*

import model.LocationModel
import model.ParkingLotModel
import model.SpotData
import org.jetbrains.anko.*
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class HomeActivity : AppCompatActivity(), AreaListener, AnkoLogger {

    var locations = ArrayList<LocationModel>()
    lateinit var app: MainApp

    //called when an area card is clicked by user
    //sends user to the activity associated with card clicked
    override fun onAreaClick(location: LocationModel) {
        if(location.lotNum == 2){
            startActivity(intentFor<MainActivity>())
        }
    }

    /* called when activity is created
       Set up of app features such as which layout is loaded,
       the app bar, tool bar, app deceleration, and layout manager.
       Calls loadAreas Function and get data function.
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        setSupportActionBar(toolbarAdd)
        toolbarAdd.title = "Parking Tracker"
        loadAreas()
        app = application as MainApp
        recyclerView.isVisible = false
        getData()
    }

    /*
     used for loading area cards
     Calls loaddata and showAreas fuctions
      */
    private fun loadAreas(){
        loaddata()
        showAreas(locations)
    }

    /*
    used for displaying the area cards to the UI.
    Uses the LocationAdapter class to correctly fill the cards
    with data
     */
    fun showAreas(locations: List<LocationModel>){
        recyclerView.adapter = LocationAdapter(locations, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    /*
    used for loading area data.
    as of right now only one place uses the system(CMU).
    As the system is implemented to more places this would eventually
    be a call to the server for data population
    */
    private fun loaddata(){
        var area = LocationModel()

        area.lotNum = 2
        area.title = "Central Michigan University"

        locations.add(area.copy())
    }

     /*
     Web server call. Calls web server and receives a json object.
     This operation is ran in the background. Once data is received from
     call the data is sent to the fromJson() function.
      */
    private fun getData(){
         progressBar.isVisible = true
        doAsync {
            val connection = URL("https://parking-tracker.azurewebsites.net/parking_picture/api/parking_data/1/").openConnection() as HttpsURLConnection
            connection.connect()
            val text = connection.inputStream.use{it.reader().use{reader ->reader.readText()}}
//            val jsonText = text

            uiThread {
                toJson(text)
                progressBar.isVisible = false
                recyclerView.isVisible = true
            }
        }

    }

    /*
    Used for taking the data from the server and parsing it in
    to a SpotData object.
     */
    private fun toJson(text: String) {
        var spot_list = Gson().fromJson(text, SpotData::class.java )
        info(spot_list)
        app.spotData = spot_list

    }

}
