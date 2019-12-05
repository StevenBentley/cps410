package com.example.centralparkingtracker

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbarAdd
import kotlinx.android.synthetic.main.parking_lot_2.navigation_view
import kotlinx.android.synthetic.main.parking_lot_2.parkinglot_2
import kotlinx.android.synthetic.main.parking_lot_2.s0open
import kotlinx.android.synthetic.main.parking_lot_2.s1taken
import kotlinx.android.synthetic.main.parking_lot_2.s2taken
import kotlinx.android.synthetic.main.parking_lot_2.s3taken
import kotlinx.android.synthetic.main.parking_lot_2.s4open
import kotlinx.android.synthetic.main.parking_lot_2.s5taken
import kotlinx.android.synthetic.main.parking_lot_2.*
import org.jetbrains.anko.colorAttr
import org.jetbrains.anko.intentFor


/*
    This activity controls the functionality of parking lot 2
 */
class ParkingLotActivity2: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    //Called when activity created. sets supports for necessary elements for ui.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parking_lot_2)
        setSupportActionBar(toolbarAdd)
        toolbarAdd.title = "Parking Lot #2"
        dummydata()
        addDrawer()
        val refresher: SwipeRefreshLayout = findViewById(R.id.pull_refresh2)
        refresher.setOnRefreshListener(){

            refresher.isRefreshing = false
        }
    }

    /*
    Adds drawer menu on the side of ui
     */
    private fun addDrawer(){
        val toggle = ActionBarDrawerToggle(Activity(), parkinglot_2, toolbarAdd, R.string.nav_open, R.string.nav_closed)
        parkinglot_2.addDrawerListener(toggle)
        toggle.syncState()
        navigation_view.setNavigationItemSelectedListener (this)
    }

    /*
        Used when user touches an item in the drawers menu.
     */
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.p1 ->{
                startActivity(intentFor<ParkingLotActivity>())
                finish()
            }
            R.id.p2 ->{
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
        parkinglot_2.closeDrawer(GravityCompat.START)
        return true
    }

    /*
    used for populating fake data for lot 2
     */
    private fun dummydata(){
        s0open.isVisible = true
        s1taken.isVisible = true
        s2taken.isVisible = true
        s3taken.isVisible = true
        s4open.isVisible = true
        s5taken.isVisible = true
        s6open.isVisible = true
        s7taken.isVisible = true

    }

    /*
        Called when the back button is pressed by user. Taking user to previous activity.
     */
    override fun onBackPressed() {
        startActivity(intentFor<MainActivity>())


        super.onBackPressed()
    }
}