package com.example.centralparkingtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.parking_lot_card.view.*
import model.ParkingLotModel

class ParkingLotAdapter constructor(private var parkinglots: List<ParkingLotModel>,
                                  private val listener: ParkingLotListener) :
    RecyclerView.Adapter<ParkingLotAdapter.MainHolder>(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    var parkinglot = ParkingLotModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.parking_lot_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        parkinglot = parkinglots[holder.adapterPosition]
        holder.bind(parkinglot, listener)
        holder.mapView.onCreate(null)
        holder.mapView.getMapAsync(this)

    }

    override fun getItemCount(): Int = parkinglots.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mapView = itemView.findViewById(R.id.map) as MapView
        fun bind(parkingLot: ParkingLotModel, listener: ParkingLotListener) {
            itemView.parkingLotName.text = "${parkingLot.title}: "
            itemView.parkingLotOccup.text = "Number of Open Parking Spots: ${parkingLot.spotTotal - parkingLot.spotsTaken}"
            itemView.parkingLotInfo.text = parkingLot.info
            itemView.setOnClickListener{listener.onParkingLotClick(parkingLot)}
        }
    }
    override fun onMapReady(map: GoogleMap){
        mMap = map
        var loc : LatLng = LatLng(parkinglot.latitude, parkinglot.longitude)

        val options = MarkerOptions()
            .title("Parking Lot Loction")
            .snippet("GPS : " + loc.toString())
            .draggable(true)
            .position(loc)

        //Coptions.center(loc)

        //c =  mMap.addCircle(Coptions)

        mMap.addMarker(options)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 17f))
    }
}
