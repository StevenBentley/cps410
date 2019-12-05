package com.example.centralparkingtracker

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.toolbarAdd
import kotlinx.android.synthetic.main.parking_lot_1.*
import kotlinx.android.synthetic.main.parking_lot_1.navigation_view
import kotlinx.android.synthetic.main.parking_lot_1.s0open
import kotlinx.android.synthetic.main.parking_lot_1.s0taken
import kotlinx.android.synthetic.main.parking_lot_1.s1open
import kotlinx.android.synthetic.main.parking_lot_1.s1taken
import kotlinx.android.synthetic.main.parking_lot_1.s3open
import model.SpotData
import org.jetbrains.anko.*
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/*
This activity controls the functionality of parking lot 1
 */
class ParkingLotActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, AnkoLogger {

    lateinit var app: MainApp

//Called when activity created. sets supports for necessary elements for ui.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parking_lot_1)
        setSupportActionBar(toolbarAdd)
        toolbarAdd.title = "Parking Lot #1"
        //dummydata()
        addDrawer()
        app = application as MainApp
        displayData(app.spotData)

        val refresher: SwipeRefreshLayout = findViewById(R.id.pull_refresh)
        refresher.setOnRefreshListener(){
            getData()
            refresher.isRefreshing = false
        }
    }

    /*
    Called when pull down refresh is initiated by user. Used for getting the data from the server. Used doAsync to do a
    web request in the background. uiThread is used for connecting the background service to the Ui.
     */
    private fun getData(){
        doAsync {
            val connection = URL("http://141.209.213.66:8080/parking_picture/api/parking_data/1/").openConnection() as HttpURLConnection
            connection.connect()
            val text = connection.inputStream.use{it.reader().use{reader ->reader.readText()}}
//            val jsonText = text

            uiThread {
                toJson(text)
            }
        }
    }

    /*
    Takes the data from the server and turns it in to an object
     */
    private fun toJson(text: String) {
        var spot_list = Gson().fromJson(text, SpotData::class.java )
        info(spot_list)
        displayData(spot_list)

    }

    /*
    Adds drawer menu on the side of ui
     */
    private fun addDrawer(){
        val toggle = ActionBarDrawerToggle(Activity(), parkinglot_1, toolbarAdd, R.string.nav_open, R.string.nav_closed)
        parkinglot_1.addDrawerListener(toggle)
        toggle.syncState()
        navigation_view.setNavigationItemSelectedListener (this)
    }

/*
    Used when user touches an item in the drawers menu.
 */
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.p1 ->{

            }
            R.id.p2 ->{
                startActivity(intentFor<ParkingLotActivity2>())
                finish()
            }
            R.id.home->{
                startActivity(intentFor<HomeActivity>())
                finish()
            }
            R.id.cmu_list->{
                startActivity(intentFor<MainActivity>())
                finish()
            }

        }
        parkinglot_1.closeDrawer(GravityCompat.START)
        return true
    }
    /*
    used for populating data before server was up.

    private fun dummydata(){
        s0open.isVisible = true
        s1taken.isVisible = true
        s2taken.isVisible = true
        s3taken.isVisible = true
        s4open.isVisible = true
        s5open.isVisible = true

    }
    */

    /*
    Called when back button is pressed, taking user to previous activity
     */
    override fun onBackPressed() {
        startActivity(intentFor<MainActivity>())


        super.onBackPressed()
    }

    /*
    Uses the data from the server to correctly display which spots are open
    and which spots are taken in the praking lot diagram.
     */
    private fun displayData(spot_list: SpotData){
        info("displayData: $spot_list")
        if(spot_list.space1){
            s0open.isVisible = true
            s0taken.isVisible = false
        }
        else if(!spot_list.space1){
            s0open.isVisible = false
            s0taken.isVisible = true
        }

        if(spot_list.space2){
            s1open.isVisible = true
            s1taken.isVisible = false
        }
        else if(!spot_list.space2){
            s1open.isVisible = false
            s1taken.isVisible = true
        }
        if(spot_list.space3){
            s2open.isVisible = true
            s2taken.isVisible = false
        }
        else if(!spot_list.space3){
            s2open.isVisible = false
            s2taken.isVisible = true
        }
        if(spot_list.space4){
            s3open.isVisible = true
            s3taken.isVisible = false
        }
        else if(!spot_list.space4){
            s3open.isVisible = false
            s3taken.isVisible = true
        }
        if(spot_list.space5){
            s4open.isVisible = true
            s4taken.isVisible = false
        }
        else if(!spot_list.space5){
            s4open.isVisible = false
            s4taken.isVisible = true
        }
        if(spot_list.space6){
            s5open.isVisible = true
            s5taken.isVisible = false
        }
        else if(!spot_list.space6){
            s5open.isVisible = false
            s5taken.isVisible = true
        }
    }
}