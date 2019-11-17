package com.example.centralparkingtracker

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
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



class ParkingLotActivity2: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parking_lot_2)
        setSupportActionBar(toolbarAdd)
        toolbarAdd.title = "Parking Lot #2"
        dummydata()
        addDrawer()
    }


    private fun addDrawer(){
        val toggle = ActionBarDrawerToggle(Activity(), parkinglot_2, toolbarAdd, R.string.nav_open, R.string.nav_closed)
        parkinglot_2.addDrawerListener(toggle)
        toggle.syncState()
        navigation_view.setNavigationItemSelectedListener (this)
    }


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

    override fun onBackPressed() {
        startActivity(intentFor<MainActivity>())


        super.onBackPressed()
    }
}